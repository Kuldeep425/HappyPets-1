package com.example.backend.backend.Reposistory;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.backend.backend.collections.ChatRoom;

public interface ChatRoomRepo extends MongoRepository<ChatRoom,String>{

    Optional<String> findBySenderIdAndRecipientId(String senderId, String recipientId);
    
}
