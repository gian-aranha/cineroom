package br.com.cineroom.api.controllers;

import br.com.cineroom.api.dtos.user.UserDTO;
import br.com.cineroom.api.dtos.user.UserReturnDTO;
import br.com.cineroom.api.entities.Credential;
import br.com.cineroom.api.entities.User;
import br.com.cineroom.api.repositories.CredentialRepository;
import br.com.cineroom.api.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CredentialRepository credentialRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO, UriComponentsBuilder uriBuilder) {
        User user = new User(userDTO);
        userRepository.save(user);

        Credential credential = new Credential(user, userDTO);
        credentialRepository.save(credential);

        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new UserReturnDTO(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReturnDTO> listUser(@PathVariable Long id) {
        User user = userRepository.getReferenceById(id);

        return ResponseEntity.ok(new UserReturnDTO(user));
    }
}
