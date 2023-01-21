package com.example.backend.backend.collections;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="chatMessage")
public class ChatMessage {
   
   @Id
   private String id;
   private String senderId;
   private String recipientId;
   private String senderName;
   private String recipientName;
   private String message;
}
