package br.com.cineroom.api.dtos.session;

import br.com.cineroom.api.entities.Session;
import br.com.cineroom.api.dtos.movie.MovieDTO;;

public record SessionReturnDTO(
        Long id,
        String code,
        String category,
        Integer usersLimit,
        String status,
        String content,
        String createdAt,
        Long userId,
        MovieDTO movie) {
    public SessionReturnDTO(Session session) {
        this(
                session.getId(),
                session.getCode(),
                session.getCategory().name(),
                session.getUsersLimit(),
                session.getStatus().name(),
                session.getContent(),
                session.getCreatedAt().toString(),
                session.getUser().getId(),
                session.getMovie() != null
                        ? new MovieDTO(
                                session.getMovie().getId(),
                                session.getMovie().getTitle(),
                                session.getMovie().getImg(),
                                session.getMovie().getOverview(),
                                session.getMovie().getVoteAverage())
                        : null);
    }
}
