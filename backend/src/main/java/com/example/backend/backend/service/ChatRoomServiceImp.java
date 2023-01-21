package com.example.backend.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.backend.backend.Reposistory.ChatRoomRepo;
import com.example.backend.backend.collections.ChatMessage;
import com.example.backend.backend.collections.ChatRoom;

@Service
public class ChatRoomServiceImp implements ChatRoomService {
    
    @Autowired private ChatRoomRepo chatRoomRepo;
     
    @Override
    public void save(ChatMessage cMessage) {
        String roomId1=cMessage.getSenderId()+cMessage.getRecipientId();
        String roomId2=cMessage.getRecipientId()+cMessage.getSenderId();
        
        // creating room for sender
        if(chatRoomRepo.findById(roomId1).isPresent()){
            ChatRoom chatRoom=chatRoomRepo.findById(roomId1).get();
            chatRoom.getMessages().add(cMessage);
            chatRoomRepo.save(chatRoom);
        }
        else{
            List<ChatMessage>l=new ArrayList<>();
            l.add(cMessage);
            ChatRoom chatRoom=new ChatRoom(roomId1,l);
            chatRoom=chatRoomRepo.save(chatRoom);
        }

        //creating room for receiver
        if(chatRoomRepo.findById(roomId2).isPresent()){
            ChatRoom chatRoom=chatRoomRepo.findById(roomId2).get();
            chatRoom.getMessages().add(cMessage);
            chatRoomRepo.save(chatRoom);
        }
        else{
            List<ChatMessage>l=new ArrayList<>();
            l.add(cMessage);
            ChatRoom chatRoom=new ChatRoom(roomId2,l);
            chatRoom=chatRoomRepo.save(chatRoom);
        }

        
    }


    // to find all chats between two users
    @Override
    public List<ChatMessage> findAllChat(String senderId, String receiverId) {
        List<ChatMessage>messages=null;
        if(chatRoomRepo.findById(senderId+receiverId).isPresent()) messages=chatRoomRepo.findById(senderId+receiverId).get().getMessages();
        return messages;
    }
    
}
