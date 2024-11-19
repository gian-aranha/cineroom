package br.com.cineroom.api.dtos.session;

import br.com.cineroom.api.entities.Session;

public record SessionReturnDTO(
        Long id,
        String code,
        String category,
        Integer usersLimit,
        String status,
        String content,
        String createdAt,
        String user,
        Long movie_id
) {
    public SessionReturnDTO(Session session) {
        this(
                session.getId(),
                session.getCode(),
                session.getCategory().name(),
                session.getUsersLimit(),
                session.getStatus().name(),
                session.getContent(),
                session.getCreatedAt().toString(),
                session.getUser().getUsername(),
                session.getMovie() != null ? session.getMovie().getId() : null
        );
    }
}
