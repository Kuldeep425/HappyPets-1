package com.example.backend.backend.service;

import java.util.List;
import java.util.Optional;

import javax.mail.Multipart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.backend.Model.LoginModel;
import com.example.backend.backend.Model.ResetPasswordModel;
import com.example.backend.backend.collections.Notification;
import com.example.backend.backend.collections.User;

public interface UserService {

    User registerUser(User user);
   

    User getUserByUserId(String user_id);

    String VerifyTokenAndValidateUser(String token);

    ResponseEntity<?> loginUser(LoginModel loginModel) throws Exception;

    ResponseEntity<?> logoutUser(String userId);


    List<User> getAllUsers();


    User updateUser(User user, MultipartFile file);


    ResponseEntity<?> resetPassword(ResetPasswordModel resetPasswordModel) throws Exception;

    ResponseEntity<?> sendNotificationToOwner(String userId,String ownerId,String petId) throws Exception;


    List<Notification> getAllNotification(String userId);

    
    
}
