package com.example.backend.backend.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.example.backend.backend.collections.*;

public interface ChatMessageService {
    public ChatMessage save(ChatMessage chatMessage);

    public List<ChatMessage> findChatMessages(String senderId, String recipientId);

    public Object findById(String id);
}
