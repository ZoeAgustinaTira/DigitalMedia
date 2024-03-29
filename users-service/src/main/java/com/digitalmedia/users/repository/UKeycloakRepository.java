package com.digitalmedia.users.repository;

import com.digitalmedia.users.model.UKeycloak;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class UKeycloakRepository implements IUKeycloakRepository{

    @Autowired
    private Keycloak keycloakClient;

    @Value("${digitalMedia.keycloak.realm}")
    private String realm;

    @Override
    public List<UKeycloak> findByFirstName(String firstName){
        List<UserRepresentation> users= keycloakClient.realm(realm).users().search(firstName);
        return users.stream().map(userRepresentation -> toUserKeycloak(userRepresentation)).collect(Collectors.toList());
    }

    @Override
    public UKeycloak findById(String id){
        UserRepresentation users= keycloakClient.realm(realm).users().get(id).toRepresentation();
        return toUserKeycloak(users);
    }

    @Override
    public UKeycloak updateNationality(String id, String nationality){
        UserResource userResource= keycloakClient.realm(realm).users().get(id);
        UserRepresentation userRepresentation= userResource.toRepresentation();
        Map<String, List<String>> attributes= new HashMap<>();
        attributes.put("nacionalidad", List.of(nationality));
        userRepresentation.setAttributes(attributes);
        userResource.update(userRepresentation);
        return  toUserKeycloak(userRepresentation);
    }

    @Override
    public List<UKeycloak> findNoAdmin(){

        List<GroupRepresentation> groupRepresentations = keycloakClient
                .realm(realm)
                .groups()
                .groups();

        List<UserRepresentation> allUserRepresentations = new ArrayList<>();
        List<UserRepresentation> adminUserRepresentations = new ArrayList<>();

        allUserRepresentations=keycloakClient
                .realm(realm)
                .users()
                .list();

        for(GroupRepresentation g: groupRepresentations) {
            if (g.getName().contains("admin"))
            {adminUserRepresentations=keycloakClient.realm(realm)
                    .groups()
                    .group(g.getId())
                    .members();
                break;
            }
        }

        List<UserRepresentation> finalUser = allUserRepresentations;

        for(int f=0; f<allUserRepresentations.size();f++)
            for(int i=0;i<adminUserRepresentations.size();i++)
                if(allUserRepresentations.get(f).getId().contains(adminUserRepresentations.get(i).getId()))
                    finalUser.remove(f);


        List<UKeycloak> userList = finalUser.stream().map(this::toUserKeycloak).collect(Collectors.toList());

        return userList;
    }

    @Override
    public List<UKeycloak> findByUsername(String username) {
        List<UserRepresentation> users= keycloakClient.realm(realm).users().search(username);
        return users.stream().map(userRepresentation -> toUserKeycloak(userRepresentation)).collect(Collectors.toList());
    }

    public UKeycloak toUserKeycloak(UserRepresentation userRepresentation){

        String nationality= null;
        try {
            nationality= userRepresentation.getAttributes().get("nationality").get(0);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return new UKeycloak(userRepresentation.getId(), userRepresentation.getUsername(), userRepresentation.getEmail(), userRepresentation.getFirstName(), userRepresentation.getGroups(), nationality); //.group(userRepresentation.getId()).members().collect(Collectors.toList()  .stream().map(group -> group.getName()).collect(Collectors.toList())
    }
}
