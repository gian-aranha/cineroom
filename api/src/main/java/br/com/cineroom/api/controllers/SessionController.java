package br.com.cineroom.api.controllers;

import br.com.cineroom.api.dtos.session.SessionDTO;
import br.com.cineroom.api.dtos.session.SessionReturnDTO;
import br.com.cineroom.api.entities.Session;
import br.com.cineroom.api.repositories.SessionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/sessions")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    // Criar uma nova sessão usando DTO
    @PostMapping
    @Transactional
    public ResponseEntity<?> createSession(@RequestBody @Valid SessionDTO sessionDTO, UriComponentsBuilder uriBuilder) {
        Session session = new Session(sessionDTO);
        session.setCreatedAt(LocalDateTime.now()); // Define a data de criação
        sessionRepository.save(session);

        var uri = uriBuilder.path("/sessions/{id}").buildAndExpand(session.getId()).toUri();

        return ResponseEntity.created(uri).body(new SessionReturnDTO(session));
    }

    // Retornar todas as sessões
    @GetMapping
    public ResponseEntity<Iterable<SessionReturnDTO>> getAllSessions() {
        var sessions = sessionRepository.findAll().stream()
                .map(SessionReturnDTO::new)
                .toList();
        return ResponseEntity.ok(sessions);
    }

    // Retornar sessão por ID
    @GetMapping("/{id}")
    public ResponseEntity<SessionReturnDTO> getSessionById(@PathVariable Long id) {
        var session = sessionRepository.findById(id).map(SessionReturnDTO::new);
        return session.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Retornar sessões por usuário
    @GetMapping("/user/{userId}")
    public ResponseEntity<Iterable<SessionReturnDTO>> getSessionByUser(@PathVariable Long userId) {
        var sessions = sessionRepository.findByUserId(userId).stream()
                .map(SessionReturnDTO::new)
                .toList();
        return ResponseEntity.ok(sessions);
    }

    // Retornar sessão por código
    @GetMapping("/code/{code}")
    public ResponseEntity<SessionReturnDTO> getSessionByCode(@PathVariable String code) {
        return sessionRepository.findByCode(code)
                .map(session -> ResponseEntity.ok(new SessionReturnDTO(session)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Atualizar sessão
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<SessionReturnDTO> updateSession(@PathVariable Long id, @RequestBody @Valid SessionDTO sessionDTO) {
        return sessionRepository.findById(id).map(existingSession -> {
            existingSession.updateFromDTO(sessionDTO);
            sessionRepository.save(existingSession);
            return ResponseEntity.ok(new SessionReturnDTO(existingSession));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar sessão por ID
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteSession(@PathVariable Long id) {
        if (sessionRepository.existsById(id)) {
            sessionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Deletar sessões por usuário
    @DeleteMapping("/user/{userId}")
    @Transactional
    public ResponseEntity<?> deleteSessionByUser(@PathVariable Long userId) {
        var sessions = sessionRepository.findByUserId(userId);
        sessions.forEach(session -> sessionRepository.deleteById(session.getId()));
        return ResponseEntity.ok().build();
    }
}
