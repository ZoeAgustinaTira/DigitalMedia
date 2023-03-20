package com.digitalmedia.users.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "users_keycloak")
public class UKeycloak {
    @Id
    private String id;
    private String username;
    private String email;
    private String firstName;
    private List<String> groups;
    private String nationality;

    public UKeycloak(String id, String username, String email, String firstName, List<String> groups, String nationality) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.groups = groups;
        this.nationality = nationality;
    }
}
