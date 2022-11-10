package com.example.backend.backend.service;

import java.util.Optional;

public interface ChatRoomService {
    
    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist);
}
