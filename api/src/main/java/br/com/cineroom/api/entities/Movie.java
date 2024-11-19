package br.com.cineroom.api.entities;

import br.com.cineroom.api.dtos.movie.MovieDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "movies")
@Entity(name = "Movie")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String image;
    private String overview;
    private Double rating;

    public Movie(MovieDTO movieDTO) {
        this.title = movieDTO.title();
        this.image = movieDTO.image();
        this.overview = movieDTO.overview();
        this.rating = movieDTO.rating();
    }
}
