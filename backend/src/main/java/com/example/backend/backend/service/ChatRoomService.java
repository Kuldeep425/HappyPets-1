package com.example.backend.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.example.backend.backend.collections.ChatMessage;

public interface ChatRoomService {
    public void save(ChatMessage cMessage);
    public List<ChatMessage> findAllChat(String senderId,String receiverId);
}
