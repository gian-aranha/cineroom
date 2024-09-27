package br.com.cineroom.api.controllers;

import br.com.cineroom.api.dtos.credential.CredentialDTO;
import br.com.cineroom.api.repositories.CredentialRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private CredentialRepository credentialRepository;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid CredentialDTO credentialsDTO) {
        var credential = credentialRepository.findByEmail(credentialsDTO.email());

        if (credential == null || !credential.getPassword().equals(credentialsDTO.password())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }
}
