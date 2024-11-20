package br.com.cineroom.api.controllers;

import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/send")
    public void handleMessage(@Payload Map<String, Object> message) {
        String room = (String) message.get("room");

        // Verificar se o room foi passado corretamente
        if (room == null || room.isEmpty()) {
            System.err.println("Erro: room não fornecido no payload!");
            return;
        }
        // Enviar mensagem para o tópico dinâmico
        messagingTemplate.convertAndSend("/topic/room/" + room, message);

    }

    @MessageMapping("/start")
    public void startSession(@Payload Map<String, String> payload) {
        String room = (String) payload.get("room");

        if (room != null) {
            // Notifica todos os usuários conectados à sala
            messagingTemplate.convertAndSend("/topic/room/" + room,
                    Map.of("type", "SESSION_STARTED", "message", "Sessão iniciada!"));
        }
    }

    @MessageMapping("/like")
    public void likeMovie(@Payload Map<String, Object> payload) {
        String room = (String) payload.get("room");
        String movieId = (String) payload.get("movieId");
        int usersLimit = Integer.parseInt((String)payload.get("usersLimit"));

        if (room == null || movieId == null) {
            System.err.println("Erro: room ou movieId não fornecido no payload!");
            return;
        }

        // Obter ou criar o mapa de likes para a sala
        roomLikesMap.putIfAbsent(room, new HashMap<>());
        Map<String, Integer> likesMap = roomLikesMap.get(room);

        // Atualizar a contagem de likes do filme
        likesMap.put(movieId, likesMap.getOrDefault(movieId, 0) + 1);

        // Verificar se o limite de likes foi atingido
        if (likesMap.get(movieId) >= usersLimit) {
            // Notificar que o filme foi escolhido
            messagingTemplate.convertAndSend("/topic/room/" + room,
                    Map.of("type", "MOVIE_SELECTED", "movieId", movieId, "message", "Filme selecionado por todos!"));

            // Remover o filme do mapa (opcional, para evitar novas notificações)
            likesMap.remove(movieId);
        }
    }
}
