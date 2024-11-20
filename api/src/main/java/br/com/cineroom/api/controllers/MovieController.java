package br.com.cineroom.api.controllers;

import br.com.cineroom.api.dtos.movie.MovieDTO;
import br.com.cineroom.api.dtos.movie.MovieExistsDTO;
import br.com.cineroom.api.dtos.movie.MovieReturnDTO;
import br.com.cineroom.api.entities.Movie;
import br.com.cineroom.api.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import br.com.cineroom.api.services.MovieService;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/")
    public String getPopularMovies() {
        return movieService.getPopularMovies();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        var movie = movieRepository.getReferenceById(id);

        return ResponseEntity.ok(new MovieReturnDTO(movie));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createMovie(@RequestBody MovieDTO movieDTO, UriComponentsBuilder uriBuilder) {
        var movieByTitle = movieRepository.findByTitle(movieDTO.title());
        if (movieByTitle != null) {
            return ResponseEntity.badRequest().body(new MovieExistsDTO("Movie already exists", movieByTitle.getId()));
        }

        var movie = new Movie(movieDTO);
        movieRepository.save(movie);

        var uri = uriBuilder.path("/movies/{id}").buildAndExpand(movie.getId()).toUri();

        return ResponseEntity.created(uri).body(new MovieReturnDTO(movie));
    }

    @PostMapping("/by-genre")
    public String getMoviesByGenres(@RequestBody List<String> genres) {
        try {
            return movieService.getMoviesByGenres(genres);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
