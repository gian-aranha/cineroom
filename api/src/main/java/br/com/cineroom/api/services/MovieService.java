package br.com.cineroom.api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class MovieService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String convertMovies(String genreName){
        String genreId = "0";

        switch (genreName) {
            case "action":
                genreId = "28";
                break;
            case "adventure":
                genreId = "12";
                break;
            case "animation":
                genreId = "16";
                break;
            case "comedy":
                genreId = "35";
                break;
            case "crime":
                genreId = "80";
                break;
            case "documentary":
                genreId = "99";
                break;
            case "drama":
                genreId = "18";
                break;
            case "family":
                genreId = "10751";
                break;
            case "fantasy":
                genreId = "14";
                break;
            case "history":
                genreId = "36";
                break;
            case "horror":
                genreId = "27";
                break;
            case "music":
                genreId = "10402";
                break;
            case "mystery":
                genreId = "9648";
                break;
            case "romance":
                genreId = "10749";
                break;
            case "science fiction":
                genreId = "878";
                break;
            case "tv movie":
                genreId = "10770";
                break;
            case "thriller":
                genreId = "53";
                break;
            case "war":
                genreId = "10752";
                break;
            case "western":
                genreId = "37";
                break;
            default:
                genreId = "0";
                break;
        }

        return genreId;
    }

    public String getPopularMovies() {
        String tmdbUrl = "https://api.themoviedb.org/3/movie/popular";
        String url = String.format("%s?api_key=%s", tmdbUrl, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String getMoviesByGenre(String genreName) throws Exception {
        String tmdbUrl = "https://api.themoviedb.org/3/discover/movie";
        String genreId = convertMovies(genreName);
        if(genreId.equals("0")){
            throw new Exception("Invalid genre");
        }
        String url = String.format("%s?api_key=%s&with_genres=%s", tmdbUrl, apiKey, genreId);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
