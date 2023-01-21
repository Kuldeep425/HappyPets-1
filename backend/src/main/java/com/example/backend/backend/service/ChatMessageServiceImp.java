package com.example.backend.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.backend.Reposistory.ChatMessageRepo;
import com.example.backend.backend.collections.ChatMessage;

@Service
public class ChatMessageServiceImp implements ChatMessageService {

    @Autowired 
    private ChatMessageRepo chatMessageRepo;
    @Autowired
    private ChatRoomService chatRoomService;
   

    // to save message in ChatMessage and return the message
    @Override
    public ChatMessage save(ChatMessage chatMessage){
         ChatMessage cMessage=chatMessageRepo.save(chatMessage);
         chatRoomService.save(cMessage);
         return cMessage;
    }

   // find all the chat messsage between two user
    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        return chatRoomService.findAllChat(senderId, recipientId);
    }

    @Override
    public Object findById(String id) {
          return chatMessageRepo.findById(id);
    }
}
