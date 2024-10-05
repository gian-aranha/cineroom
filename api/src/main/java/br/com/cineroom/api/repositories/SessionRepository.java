package br.com.cineroom.api.repositories;

import br.com.cineroom.api.entities.Session;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUserId(Long userId);
    Optional<Session> findByCode(String code);
}
