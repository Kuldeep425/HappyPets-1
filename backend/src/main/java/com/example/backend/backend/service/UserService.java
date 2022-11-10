package com.example.backend.backend.service;

import java.util.List;
import java.util.Optional;

import javax.mail.Multipart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.backend.Model.LoginModel;
import com.example.backend.backend.collections.User;

public interface UserService {

    User registerUser(User user,MultipartFile file);
   

    Optional<User> getUserByUserId(String user_id);

    String VerifyTokenAndValidateUser(String token);

    String loginUser(LoginModel loginModel);

    ResponseEntity<?> logoutUser(String userId);


    List<User> getAllUsers();
    
}
