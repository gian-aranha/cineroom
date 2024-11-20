package br.com.cineroom.api.listeners;

import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import br.com.cineroom.api.components.WebSocketSessionTracker;

@Component
public class WebSocketEventListener {

    private final WebSocketSessionTracker tracker;
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketEventListener(WebSocketSessionTracker tracker, SimpMessagingTemplate messagingTemplate) {
        this.tracker = tracker;
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        String room = accessor.getFirstNativeHeader("room");
        int usersLimit = Integer.parseInt(accessor.getFirstNativeHeader("usersLimit"));
        String uId = accessor.getFirstNativeHeader("uId");

        if (room != null) {
            // Adiciona o usuário à sala
            if (tracker.getRoomCount(room) >= usersLimit) {
                new Thread(() -> {
                    try {
                        Thread.sleep(500); // 500ms para garantir a inscrição
                        messagingTemplate.convertAndSend("/topic/room/" + room,
                                Map.of("type", "SESSION_FULL", "uId", uId));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            } else {
                tracker.addConnection(room, sessionId);

                // Aguarda brevemente antes de enviar a notificação de contagem
                new Thread(() -> {
                    try {
                        Thread.sleep(500); // 500ms para garantir a inscrição
                        notifyRoomCount(room);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            }

        }
    }

    @EventListener
    public void handleSessionDisconnected(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();

        // Supondo que a sala está associada ao sessionId
        tracker.roomConnections.forEach((room, connections) -> {
            if (connections.contains(sessionId)) {
                tracker.removeConnection(room, sessionId);
                notifyRoomCount(room);
            }
        });
    }

    private void notifyRoomCount(String room) {
        int userCount = tracker.getRoomCount(room);
        messagingTemplate.convertAndSend("/topic/room/" + room, Map.of("type", "USER_COUNT", "count", userCount));
    }
}
