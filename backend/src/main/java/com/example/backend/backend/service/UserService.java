package com.example.backend.backend.service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.example.backend.backend.Model.LoginModel;
import com.example.backend.backend.collections.User;

public interface UserService {

    User registerUser(User user);
   

    Optional<User> getUserByUserId(String user_id);

    String VerifyTokenAndValidateUser(String token);

    String loginUser(LoginModel loginModel);

    ResponseEntity<?> logoutUser(String userId);
    
}
