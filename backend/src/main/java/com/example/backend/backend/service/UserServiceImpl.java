package com.example.backend.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import com.example.backend.backend.Model.LoginModel;
import com.example.backend.backend.Reposistory.TokenRepo;
import com.example.backend.backend.Reposistory.UserRepo;
import com.example.backend.backend.collections.Token;
import com.example.backend.backend.collections.User;
import com.example.backend.backend.config.SecurityConfigurer;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private SecurityConfigurer securityConfigurer;
    @Autowired 
    private TokenRepo tokenRepo;

    // register a user 
    @Override
    public User registerUser(User user) {
        user.setPassword(securityConfigurer.passwordEncoder().encode(user.getPassword()));
       return userRepo.save(user);
    }

    
   // to find a user based on user id
   @Override
   public Optional<User> getUserByUserId(String user_id) {
       return userRepo.findById(user_id);
   }

   // to verify token
   @Override
   public String VerifyTokenAndValidateUser(String token) {
     Token tkn=tokenRepo.findByToken(token);
     if(tkn==null) return "Invalid";
     Optional<User>user=userRepo.findById(tkn.getUserId());
     if(user==null) return "Invalid";
     Calendar cal=Calendar.getInstance();
       if(tkn.getExpirationTime().getTime()-cal.getTime().getTime()<=0){
           tokenRepo.delete(tkn);
           return "Token expired";
       }
       User u=user.get();
       u.setVerified(true);
       userRepo.save(u);
       tokenRepo.delete(tkn);
       return "valid";
   }

   // to login user
   @Override
   public String loginUser(LoginModel loginModel) {
        if(loginModel==null) return "No credential found";
        Optional<User> user=userRepo.findByEmail(loginModel.getEmail());
        if(user.isEmpty()) return "user not found associated to this email";
        if(securityConfigurer.passwordEncoder().matches(loginModel.getPassword(), user.get().getPassword())){
           user.get().setHasLoggedIn(true);
           userRepo.save(user.get());
           return user.get().getId();
        }
        return "Bad Credential";
   }

   // logout the user
   @Override
   public ResponseEntity<?> logoutUser(String userId) {
       Optional<User>user=userRepo.findById(userId);
       if(user.isEmpty()) return ResponseEntity.ok("User not found");
       user.get().setHasLoggedIn(false);
       userRepo.save(user.get());
       return ResponseEntity.ok("logout successfullly");
   }

    
    
}
