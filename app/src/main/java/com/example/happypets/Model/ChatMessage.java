package com.example.happypets.Model;

public class ChatMessage {
    private String id;
    private String senderId;
    private String recipientId;
    private String recipientName;
    private String message;
    private String senderName;

    public ChatMessage(String id, String senderId, String recipientId, String recipientName, String message, String senderName) {
        this.id = id;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.message = message;
        this.senderName = senderName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
