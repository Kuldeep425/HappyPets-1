package com.example.backend.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.backend.Model.NotificationModel;

@RestController
public class NotificationController {
    
    @Autowired private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/notification")
    public void sendTheNotification(@Payload NotificationModel notificationModel){
             
    }
}
