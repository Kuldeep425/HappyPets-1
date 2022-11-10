package com.example.backend.backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
      return chatMessageRepo.save(chatMessage);
    }

   // find all the chat messsage between two user
    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);
        var messages =chatId.map(cId -> chatMessageRepo.findByChatId(cId)).orElse(new ArrayList<>());
        return (List<ChatMessage>)messages;
    }

    @Override
    public Object findById(String id) {
          return chatMessageRepo.findByChatId(id);
    }
}
