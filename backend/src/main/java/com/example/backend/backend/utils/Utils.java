package com.example.backend.backend.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;
import com.example.backend.backend.Model.ChatMessageModel;
import com.example.backend.backend.Reposistory.UserRepo;
import com.example.backend.backend.collections.ChatMessage;

import io.github.cdimascio.dotenv.Dotenv;



@Component
public class Utils {
     
    @Autowired UserRepo userRepo;
    public ChatMessage chatMessageModelToChatMessage(ChatMessageModel chatMessageModel){
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setSenderId(chatMessageModel.getSenderId());
        chatMessage.setRecipientId(chatMessageModel.getReceiverId());
        chatMessage.setMessage(chatMessageModel.getMessage());
        chatMessage.setRecipientName(userRepo.findById(chatMessageModel.getReceiverId()).get().getName());
        chatMessage.setSenderName(userRepo.findById(chatMessageModel.getSenderId()).get().getName());
        return chatMessage;
    }

    public Cloudinary getCloudinary(){
    Dotenv dotenv=Dotenv.load();
    Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
    cloudinary.config.secure = true;
      return cloudinary;
}
    
    
}
