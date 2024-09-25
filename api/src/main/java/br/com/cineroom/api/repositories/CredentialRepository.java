package br.com.cineroom.api.repositories;

import br.com.cineroom.api.entities.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends JpaRepository<Credential, Long> {
}
