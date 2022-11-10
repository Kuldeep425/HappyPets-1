package com.example.backend.backend.service;

import com.example.backend.backend.collections.*;

public interface ChatMessageService {
    public ChatMessage save(ChatMessage chatMessage);

    public Object findChatMessages(String senderId, String recipientId);

    public Object findById(String id);
}
