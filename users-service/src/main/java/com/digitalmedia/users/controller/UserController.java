package com.digitalmedia.users.controller;

import com.digitalmedia.users.model.User;
import com.digitalmedia.users.model.dto.UKeycloakDTO;
import com.digitalmedia.users.model.dto.UserRequest;
import com.digitalmedia.users.service.IUserService;
import com.digitalmedia.users.service.UKeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
  private final IUserService userService;
  private UKeycloakService userKeycloakService;

  public UserController(IUserService userService, UKeycloakService userKeycloakService) {
    this.userService = userService;
    this.userKeycloakService = userKeycloakService;
  }

  @GetMapping("/me")
  public User getUserExtra(@RequestParam String principal) {
    return userService.validateAndGetUserExtra(principal);
  }

  @PostMapping("/me")
  @PreAuthorize("hasAuthority('GROUP_admin')")
  public User saveUserExtra(@Valid @RequestBody UserRequest updateUserRequest, @RequestParam(value = "principal") String principal) {
    Optional<User> userOptional = userService.getUserExtra(principal);
    User userExtra = userOptional.orElseGet(() -> new User(principal));
    userExtra.setAvatar(updateUserRequest.getAvatar());
    return userService.saveUserExtra(userExtra);
  }


  @GetMapping("/keycloak/{firstName}")
  @PreAuthorize("hasAuthority('GROUP_admin')")
  public List<UKeycloakDTO> findByFirstName(@PathVariable String firstName){
    return userKeycloakService.findByFirstName(firstName);
  }

  @GetMapping("/find/{username}")
  @PreAuthorize("hasAuthority('GROUP_admin') OR hasAuthority('GROUP_provider') OR hasAuthority('GROUP_client')")
  public List<UKeycloakDTO> findByUsername(@PathVariable String username){
    return userKeycloakService.findByFirstName(username);
  }

  @GetMapping("/keycloak/id/{id}")
  @PreAuthorize("hasAuthority('GROUP_admin')")
  public UKeycloakDTO findById(@PathVariable String id){
    return userKeycloakService.findById(id);
  }

  @GetMapping("/keycloak/{id}/{nationality}")
  @PreAuthorize("hasAuthority('GROUP_admin')")
  public UKeycloakDTO updateNationality(@PathVariable String id, @PathVariable String nationality){
    return userKeycloakService.updateNationality(id, nationality);
  }

  @GetMapping("/admin")
  @PreAuthorize("hasAuthority('GROUP_admin')")
  public List<UKeycloakDTO> getUsersNoAdmin() {
    return userKeycloakService.findNoAdmin();
  }

}
