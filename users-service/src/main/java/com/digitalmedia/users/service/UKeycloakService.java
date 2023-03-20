package com.digitalmedia.users.service;

import com.digitalmedia.users.model.UKeycloak;
import com.digitalmedia.users.model.dto.UKeycloakDTO;
import com.digitalmedia.users.repository.IUKeycloakRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UKeycloakService {

    private IUKeycloakRepository repositoryKeycloak;
    private ObjectMapper mapper;

    public void UserKeycloakService(IUKeycloakRepository repositoryKeycloak, ObjectMapper mapper){
        this.repositoryKeycloak= repositoryKeycloak;
        this.mapper= mapper;
    }

    public List<UKeycloakDTO> findByFirstName(String firstName){
        List <UKeycloak> users= repositoryKeycloak.findByFirstName(firstName);
        List <UKeycloakDTO> uKDTO= users.stream().map(user -> mapper.convertValue(user, UKeycloakDTO.class)).collect(Collectors.toList());
        return uKDTO;
    }

    public List<UKeycloakDTO> findByUsername(String username){
        List <UKeycloak> users= repositoryKeycloak.findByUsername(username);
        List <UKeycloakDTO> uKDTO= users.stream().map(user -> mapper.convertValue(user, UKeycloakDTO.class)).collect(Collectors.toList());
        return uKDTO;
    }

    public UKeycloakDTO findById(String id){
        UKeycloak users= repositoryKeycloak.findById(id);
        return mapper.convertValue(users, UKeycloakDTO.class);
    }

    public UKeycloakDTO updateNationality(String id, String nationality){
        UKeycloak users= repositoryKeycloak.updateNationality(id, nationality);
        return mapper.convertValue(users, UKeycloakDTO.class);
    }

    public List<UKeycloakDTO> findNoAdmin(){
        List <UKeycloak> users=repositoryKeycloak.findNoAdmin();
        List <UKeycloakDTO> uKDTO= users.stream().map(user -> mapper.convertValue(user, UKeycloakDTO.class)).collect(Collectors.toList());
        return uKDTO;
    }
}
