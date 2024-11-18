package br.com.cineroom.api.entities;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Movie {
    private Long id;
    private String title;
    private String img;
    private String overview;
    private Double voteAverage;

    public Movie(Long id, String title, String img, String overview, Double voteAverage) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.overview = overview;
        this.voteAverage = voteAverage;
    }

    public Movie() {
    }
}
