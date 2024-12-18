package br.com.cineroom.api.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    // Map para rastrear likes de filmes por sala
    private final Map<String, Map<String, Integer>> roomLikesMap = new HashMap<>();

    // Map para armazenar gêneros de cada sala
    private final Map<String, List<String>> roomGenresMap = new HashMap<>();

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    public void handleMessage(@Payload Map<String, Object> message) {
        String room = (String) message.get("room");

        if (room == null || room.isEmpty()) {
            System.err.println("Erro: room não fornecido no payload!");
            return;
        }

        messagingTemplate.convertAndSend("/topic/room/" + room, message);
    }

    @MessageMapping("/start")
    public void startSession(@Payload Map<String, String> payload) {
        String room = (String) payload.get("room");

        if (room != null) {
            messagingTemplate.convertAndSend("/topic/room/" + room,
                    Map.of("type", "SESSION_STARTED", "message", "Sessão iniciada!"));
        }
    }

    @MessageMapping("/like")
    public void likeMovie(@Payload Map<String, Object> payload) {
        String room = (String) payload.get("room");
        String movieId = (String) payload.get("movieId");
        int usersLimit = Integer.parseInt((String) payload.get("usersLimit"));

        if (room == null || movieId == null) {
            System.err.println("Erro: room ou movieId não fornecido no payload!");
            return;
        }

        roomLikesMap.putIfAbsent(room, new HashMap<>());
        Map<String, Integer> likesMap = roomLikesMap.get(room);

        likesMap.put(movieId, likesMap.getOrDefault(movieId, 0) + 1);

        if (likesMap.get(movieId) >= usersLimit) {
            messagingTemplate.convertAndSend("/topic/room/" + room,
                    Map.of("type", "MOVIE_SELECTED", "movieId", movieId, "message", "Filme selecionado por todos!"));

            likesMap.remove(movieId);
        }
    }

    @MessageMapping("/genres")
    public void sendGenres(@Payload Map<String, Object> payload) {
        String room = (String) payload.get("room");
        String genresObject = (String) payload.get("genres");

        if (room == null || genresObject == null) {
            System.err.println("Erro: room ou genres inválido no payload!");
            return;
        }

        // Usando Jackson para converter a string JSON em uma lista de strings
        List<String> genres = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // Converte a string JSON para uma lista de strings
            genres = objectMapper.readValue(genresObject, List.class);
        } catch (Exception e) {
            System.err.println("Erro ao deserializar genres: " + e.getMessage());
            return;
        }

        // Obter ou criar a lista de gêneros para o room
        roomGenresMap.putIfAbsent(room, new ArrayList<>());
        List<String> existingGenres = roomGenresMap.get(room);

        // Adicionar somente gêneros que ainda não estão na lista
        for (String genre : genres) {
            if (!existingGenres.contains(genre)) {
                existingGenres.add(genre);
            }
        }

        // Atualiza o mapa com a lista de gêneros modificada
        roomGenresMap.put(room, existingGenres);

        // Enviar os gêneros atualizados para todos os usuários conectados ao room
        messagingTemplate.convertAndSend("/topic/room/" + room,
                Map.of("type", "GENRES_RECEIVED", "genres", existingGenres));
    }

    @MessageMapping("/get/genres")
    public void getGenres(@Payload Map<String, String> payload) {
        String room = payload.get("room");

        if (room == null) {
            System.err.println("Erro: room não fornecido no payload!");
            return;
        }

        // Recuperar os gêneros da sala ou uma lista padrão vazia
        List<String> genres = roomGenresMap.getOrDefault(room, new ArrayList<>());

        // Enviar os gêneros para o solicitante
        messagingTemplate.convertAndSend("/topic/room/" + room,
                Map.of("type", "GENRES_LIST", "genres", genres));
    }

}
