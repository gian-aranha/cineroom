package br.com.cineroom.api.controllers;

import br.com.cineroom.api.dtos.movie.MovieDTO;
import br.com.cineroom.api.dtos.session.SessionDTO;
import br.com.cineroom.api.dtos.session.SessionReturnDTO;
import br.com.cineroom.api.entities.Movie;
import br.com.cineroom.api.entities.Session;
import br.com.cineroom.api.repositories.SessionRepository;
import br.com.cineroom.api.repositories.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    // create new session
    @PostMapping
    @Transactional
    public ResponseEntity<?> createSession(@RequestBody @Valid SessionDTO sessionDTO, UriComponentsBuilder uriBuilder) {
        var user = userRepository.findById(sessionDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Session session = new Session(sessionDTO, user);
        session.setCreatedAt(LocalDateTime.now());
        sessionRepository.save(session);

        var uri = uriBuilder.path("/sessions/{id}").buildAndExpand(session.getId()).toUri();

        return ResponseEntity.created(uri).body(new SessionReturnDTO(session));
    }

    // return all sessions
    @GetMapping
    public ResponseEntity<Iterable<SessionReturnDTO>> getAllSessions() {
        var sessions = sessionRepository.findAll().stream()
                .map(SessionReturnDTO::new)
                .toList();
        return ResponseEntity.ok(sessions);
    }

    // return session by ID
    @GetMapping("/{id}")
    public ResponseEntity<SessionReturnDTO> getSessionById(@PathVariable Long id) {
        var session = sessionRepository.findById(id).map(SessionReturnDTO::new);
        return session.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // return session by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<Iterable<SessionReturnDTO>> getSessionByUser(@PathVariable Long userId) {
        var sessions = sessionRepository.findByUserId(userId).stream()
                .map(SessionReturnDTO::new)
                .toList();
        return ResponseEntity.ok(sessions);
    }

    // return session by code
    @GetMapping("/code/{code}")
    public ResponseEntity<SessionReturnDTO> getSessionByCode(@PathVariable String code) {
        return sessionRepository.findByCode(code)
                .map(session -> ResponseEntity.ok(new SessionReturnDTO(session)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // update session by ID
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<SessionReturnDTO> updateSession(@PathVariable Long id,
            @RequestBody @Valid SessionDTO sessionDTO) {
        var user = userRepository.findById(sessionDTO.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return sessionRepository.findById(id).map(existingSession -> {
            existingSession.updateFromDTO(sessionDTO, user);
            sessionRepository.save(existingSession);
            return ResponseEntity.ok(new SessionReturnDTO(existingSession));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update movie field in session by ID
    @PutMapping("/{id}/movie")
    @Transactional
    public ResponseEntity<SessionReturnDTO> updateSessionMovie(@PathVariable Long id,
            @RequestBody @Valid MovieDTO movieDTO) {
        return sessionRepository.findById(id).map(session -> {
            Movie movie = new Movie(
                    movieDTO.id(),
                    movieDTO.title(),
                    movieDTO.img(),
                    movieDTO.overview(),
                    movieDTO.voteAverage());
            session.setMovie(movie);

            // Garantir que o contexto de persistência seja sincronizado
            sessionRepository.saveAndFlush(session);

            return ResponseEntity.ok(new SessionReturnDTO(session));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // delete session by ID
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteSession(@PathVariable Long id) {
        if (sessionRepository.existsById(id)) {
            sessionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // delete all sessions by user
    @DeleteMapping("/user/{userId}")
    @Transactional
    public ResponseEntity<?> deleteSessionByUser(@PathVariable Long userId) {
        var sessions = sessionRepository.findByUserId(userId);
        sessions.forEach(session -> sessionRepository.deleteById(session.getId()));
        return ResponseEntity.ok().build();
    }
}
