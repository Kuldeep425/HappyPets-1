package com.example.backend.backend.collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="Notification")
public class Notification {
    
    @Id
    private String id;
    private String senderName;
    private String receiverName;
    private String petId;
    private String petImageUrl; 

}
