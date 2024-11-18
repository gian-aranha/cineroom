package br.com.cineroom.api.configuration.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Habilita o broker de mensagens com um prefixo para tópicos
        config.enableSimpleBroker("/topic"); // Mensagens para clientes
        config.setApplicationDestinationPrefixes("/app"); // Mensagens do cliente para o servidor
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Registra o endpoint para WebSocket com suporte a SockJS
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:3000") // Ajuste conforme necessário
                .withSockJS(); // Fallback para navegadores sem suporte a WebSocket
    }
}
