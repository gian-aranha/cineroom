package br.com.cineroom.api.controllers;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    // Recebe mensagens do cliente enviadas para "/app/send"
    @MessageMapping("/send")
    @SendTo("/topic/room/{room}")
    public String sendMessage(@DestinationVariable String room, String message) {
        return "Mensagem do servidor: " + message;
    }
}
