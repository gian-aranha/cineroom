package br.com.cineroom.api.dtos.movie;

import br.com.cineroom.api.entities.Movie;

public record MovieReturnDTO(
    Long id,
    String title,
    String image,
    String overview,
    Double rating
) {

    public MovieReturnDTO(Movie movie) {
        this(
            movie.getId(),
            movie.getTitle(),
            movie.getImage(),
            movie.getOverview(),
            movie.getRating()
        );
    }
}
