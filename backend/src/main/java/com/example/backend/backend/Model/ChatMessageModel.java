package com.example.backend.backend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageModel {
    private String senderId;
    private String receiverId;
    private String message;
}
