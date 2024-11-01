package br.com.cineroom.api.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String convertMovies(String genreName) {
        switch (genreName.toLowerCase()) {
            case "action": return "28";
            case "adventure": return "12";
            case "animation": return "16";
            case "comedy": return "35";
            case "crime": return "80";
            case "documentary": return "99";
            case "drama": return "18";
            case "family": return "10751";
            case "fantasy": return "14";
            case "history": return "36";
            case "horror": return "27";
            case "music": return "10402";
            case "mystery": return "9648";
            case "romance": return "10749";
            case "science fiction": return "878";
            case "tv movie": return "10770";
            case "thriller": return "53";
            case "war": return "10752";
            case "western": return "37";
            default: return null;  // Retorna null se o gênero for inválido
        }
    }

    public String getPopularMovies() {
        String tmdbUrl = "https://api.themoviedb.org/3/movie/popular";
        String url = String.format("%s?api_key=%s", tmdbUrl, apiKey);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    public String getMoviesByGenres(List<String> genres) throws Exception {
        // Converte os nomes dos gêneros para IDs
        String genreIds = genres.stream()
            .map(this::convertMovies)
            .filter(id -> id != null)  // Filtra qualquer valor nulo (gênero inválido)
            .collect(Collectors.joining(",")); // Junta os IDs com vírgulas

        if (genreIds.isEmpty()) {
            throw new Exception("Nenhum gênero válido foi fornecido.");
        }

        String tmdbUrl = "https://api.themoviedb.org/3/discover/movie";
        String url = String.format("%s?api_key=%s&with_genres=%s", tmdbUrl, apiKey, genreIds);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
