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
        Long userId
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
                session.getUser().getId()
        );
    }
}
