package br.com.cineroom.api.dtos.movie;

public record MovieExistsDTO(
        String message,
        Long movieId
) {

    public MovieExistsDTO(String message, Long movieId) {
        this.message = message;
        this.movieId = movieId;
    }
}
