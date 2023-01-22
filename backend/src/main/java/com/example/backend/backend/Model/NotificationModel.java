package com.example.backend.backend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationModel{
         private String senderId;
         private String receiverId;
         private String petId;
}
