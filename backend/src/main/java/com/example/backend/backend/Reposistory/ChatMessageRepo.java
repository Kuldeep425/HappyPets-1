package com.example.backend.backend.Reposistory;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.backend.backend.collections.ChatMessage;

public interface ChatMessageRepo extends MongoRepository<ChatMessage,String>{

    Object findByChatId(String cId);
   
    

}
