package br.com.cineroom.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import br.com.cineroom.api.services.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public String getPopularMovies() {
        return movieService.getPopularMovies();
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
