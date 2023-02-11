package com.example.backend.backend.Reposistory;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.backend.backend.collections.Notification;
public interface NotificationRepo extends MongoRepository<Notification,String>{
    
    @Query("{receiverId:?0}")
    List<Notification> findByUserId(String userId);
    
}
