package br.com.cineroom.api.services;

import br.com.cineroom.api.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CredentialsService implements UserDetailsService {

    @Autowired
    private CredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return repository.findByEmail(username);
    }
}
