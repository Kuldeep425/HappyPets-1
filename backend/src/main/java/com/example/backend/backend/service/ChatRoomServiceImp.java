package com.example.backend.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.backend.Reposistory.ChatRoomRepo;
import com.example.backend.backend.collections.ChatRoom;

@Service
public class ChatRoomServiceImp implements ChatRoomService {
    
    @Autowired private ChatRoomRepo chatRoomRepo;
     


    // method to get ChatId if does not then create it and return 
    @Override
    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist) {
        return chatRoomRepo
                .findBySenderIdAndRecipientId(senderId, recipientId)
                //.map(ChatRoom::getChatId)
                 .or(() -> {
                    if(!createIfNotExist) {
                        return  Optional.empty();
                    }
                     var chatId =
                            String.format("%s_%s", senderId, recipientId);

                    ChatRoom senderRecipient = ChatRoom
                            .builder()
                            .chatId(chatId)
                            .senderId(senderId)
                            .recipientId(recipientId)
                            .build();

                    ChatRoom recipientSender = ChatRoom
                            .builder()
                            .chatId(chatId)
                            .senderId(recipientId)
                            .recipientId(senderId)
                            .build();
                    chatRoomRepo.save(senderRecipient);
                    chatRoomRepo.save(recipientSender);

                    return Optional.of(chatId);
                });
    }
    
}
