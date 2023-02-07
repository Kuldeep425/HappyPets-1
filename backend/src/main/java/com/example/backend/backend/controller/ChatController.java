package com.example.backend.backend.controller;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.backend.backend.Model.ChatMessageModel;
import com.example.backend.backend.Model.LoginModel;
import com.example.backend.backend.collections.ChatMessage;
import com.example.backend.backend.service.ChatMessageService;
import com.example.backend.backend.service.ChatRoomService;
import com.example.backend.backend.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.lang.Nullable;

@Controller
public class ChatController {
    
    @Autowired private SimpMessagingTemplate messagingTemplate;
    @Autowired private ChatMessageService chatMessageService;
    @Autowired private ChatRoomService chatRoomService;
    @Autowired private Utils utils;

    @MessageMapping("/chat")
    
    public void processMessage(@Payload ChatMessageModel chatMessageModel) throws Exception {
        messagingTemplate.convertAndSendToUser(
                chatMessageModel.getReceiverId(),"/queue/messages"+chatMessageModel.getSenderId(),
                chatMessageModel);
        ChatMessage chatMessage=utils.chatMessageModelToChatMessage(chatMessageModel);
        chatMessageService.save(chatMessage); 
        System.out.println(chatMessage);
    }
    
    @GetMapping("/get/messages/{senderId}/{recipientId}")
    public ResponseEntity<?> findChatMessages ( @PathVariable String senderId,
                                                @PathVariable String recipientId) {
                                                    System.out.println(senderId);
                                                    System.out.println(recipientId);
                    System.out.println(chatMessageService.findChatMessages(senderId, recipientId));
        return ResponseEntity
                .ok(chatMessageService.findChatMessages(senderId, recipientId));
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<?> findMessage ( @PathVariable String id) {
        return ResponseEntity
                .ok(chatMessageService.findById(id));
    }
}
