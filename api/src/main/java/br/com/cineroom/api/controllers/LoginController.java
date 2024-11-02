package br.com.cineroom.api.controllers;

import br.com.cineroom.api.configuration.security.JWTTokenDTO;
import br.com.cineroom.api.configuration.security.TokenService;
import br.com.cineroom.api.dtos.credential.CredentialDTO;
import br.com.cineroom.api.entities.Credential;
import br.com.cineroom.api.repositories.CredentialRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid CredentialDTO credentialsDTO) {
        var authToken = new UsernamePasswordAuthenticationToken(credentialsDTO.email(), credentialsDTO.password());
        var auth = manager.authenticate(authToken);

        var tokenJWT = tokenService.genToken((Credential) auth.getPrincipal());

        return ResponseEntity.ok(new JWTTokenDTO(tokenJWT));
    }
}
