package com.digitalmedia.users.repository;

import com.digitalmedia.users.model.UKeycloak;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface IUKeycloakRepository {
    public List<UKeycloak> findByFirstName(String firstName);

    public UKeycloak findById(String id);

    public UKeycloak updateNationality(String id, String nationality);

    public List<UKeycloak> findNoAdmin();

    public List<UKeycloak> findByUsername(String username);
}
