package br.com.cineroom.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import br.com.cineroom.api.services.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/")
    public String getPopularMovies() {
        return movieService.getPopularMovies();
    }

    @GetMapping("/by-genre")
    public String getMoviesByGenre(@RequestParam String genreName) {
        try{
            return movieService.getMoviesByGenre(genreName);
        }catch(Exception e){
            return e.getMessage();
        }
    }
}
