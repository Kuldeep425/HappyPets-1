package com.example.backend.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.backend.Model.LoginModel;
import com.example.backend.backend.Reposistory.UserRepo;
import com.example.backend.backend.collections.User;
import com.example.backend.backend.service.UserService;
import com.example.backend.backend.utils.GenerateToken;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;


@RestController
public class UserController {
    
   @Autowired private UserService userService;
   @Autowired private UserRepo userRepo;
   @Autowired private GenerateToken generateToken;
   @Autowired PasswordEncoder passwordEncoder;


   
   @PostMapping("/register/user")
   public ResponseEntity<?> registerUser(@RequestBody User user,
   final HttpServletRequest request){
    Optional<User>user2=userRepo.findByEmail(user.getEmail());
    if(user2.isPresent() && user2.get().isVerified()) return ResponseEntity.ok("Already registered Email");
    User user1=userService.registerUser(user);
    generateToken.generateToekn(user1,"Register", applicationUrl(request));
    return ResponseEntity.ok(user1.getId());
   }

   @PostMapping("/update/user")
    public ResponseEntity<?> updateUser(@RequestPart("image") MultipartFile file,
    @RequestPart("user") User user){
      return userService.updateUser(user,file);
    }
   
   @GetMapping("/verifyRegistration")
   public String verfifyUserRegisration(@RequestParam ("token") String token){
     String res=userService.VerifyTokenAndValidateUser(token); 
     if(res.equalsIgnoreCase("valid")) return "user verified successfully";
     return res;
   }
   
   @PostMapping("/login/user")
   public ResponseEntity<?> loginUser(@RequestBody LoginModel loginModel) throws Exception{
       System.out.println(loginModel.getEmail());
       return userService.loginUser(loginModel);
   }

   @GetMapping("/logout/user/{userId}")
   public ResponseEntity<?>logoutUser(@PathVariable ("userId") String userId){
      return userService.logoutUser(userId);
   }
   
    // to find the specific user 
    @GetMapping("/get/user/{userId}")
    public Optional<User> getUserByUserId(@PathVariable String userId){ 
     return userService.getUserByUserId(userId); 
     }

     //to find all users
     @GetMapping("/get/all/users")
     public List<User> getAllUser(){
       return userService.getAllUsers();
     }

     // to add favourite 

     




     private String applicationUrl(HttpServletRequest request) {
      return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }

   
   


}
