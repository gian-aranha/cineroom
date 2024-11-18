package br.com.cineroom.api.components;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

@Component
public class WebSocketSessionTracker {
    public final ConcurrentHashMap<String, CopyOnWriteArrayList<String>> roomConnections = new ConcurrentHashMap<>();

    public void addConnection(String room, String sessionId) {
        roomConnections.computeIfAbsent(room, k -> new CopyOnWriteArrayList<>()).add(sessionId);
    }

    public void removeConnection(String room, String sessionId) {
        roomConnections.getOrDefault(room, new CopyOnWriteArrayList<>()).remove(sessionId);
        if (roomConnections.get(room).isEmpty()) {
            roomConnections.remove(room);
        }
    }

    public int getRoomCount(String room) {
        return roomConnections.getOrDefault(room, new CopyOnWriteArrayList<>()).size();
    }
}