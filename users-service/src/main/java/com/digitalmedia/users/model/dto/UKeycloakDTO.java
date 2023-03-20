package com.digitalmedia.users.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UKeycloakDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private List<String> groups;
    private String nationality;

    public UKeycloakDTO(String id, String username, String email, String firstName, List<String> groups, String nationality) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.groups = groups;
        this.nationality = nationality;
    }


}
