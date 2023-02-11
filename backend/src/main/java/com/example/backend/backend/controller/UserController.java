package com.example.backend.backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.backend.Model.LoginModel;
import com.example.backend.backend.Model.ResetPasswordModel;
import com.example.backend.backend.Reposistory.UserRepo;
import com.example.backend.backend.collections.Notification;
import com.example.backend.backend.collections.User;
import com.example.backend.backend.service.UserService;
import com.example.backend.backend.utils.GenerateToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;

import org.apache.coyote.Response;
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


   // register a user
   @PostMapping("/register/user")
   public ResponseEntity<?> registerUser(@RequestBody User user,
   final HttpServletRequest request){
    System.out.println(user.getEmail());
    Optional<User>user2=userRepo.findByEmail(user.getEmail());
    if(user2.isPresent() && user2.get().isVerified())return ResponseEntity.ok("Already registered Email");
    User user1=userService.registerUser(user);
    generateToken.generateToekn(user1,"Register", applicationUrl(request));
    return ResponseEntity.ok(user1.getId());
   }

   // update user profile
   @PostMapping("/update/user")
    public ResponseEntity<?> updateUser(@RequestPart("image") MultipartFile file,
    @RequestPart("user") User user)throws IOException{
      User user1=userService.updateUser(user,file);
      if(user1==null) return ResponseEntity.ok("Error in updating profile");
      System.out.println(user1);
      System.out.println(ResponseEntity.ok(user1));
      return ResponseEntity.ok(user1.getId());
    }
   
    // to verify email 
   @GetMapping("/verifyRegistration")
   public String verfifyUserRegisration(@RequestParam ("token") String token){
     String res=userService.VerifyTokenAndValidateUser(token); 
     if(res.equalsIgnoreCase("valid")) return "user verified successfully";
     return res;
   }
   
   // to login the user 
   @PostMapping("/login/user")
   public ResponseEntity<?> loginUser(@RequestBody LoginModel loginModel) throws Exception{
       System.out.println(loginModel.getEmail());
       return userService.loginUser(loginModel);
   }
  
   // to logout the user
   @GetMapping("/logout/user/{userId}")
   public ResponseEntity<?>logoutUser(@PathVariable ("userId") String userId){
      return userService.logoutUser(userId);
   }
   
    // to find the specific user 
    @GetMapping("/get/user/{userId}")
    public User getUserByUserId(@PathVariable String userId){ 
     return userService.getUserByUserId(userId); 
     }

     //to find all users
     @GetMapping("/get/all/users")
     public List<User> getAllUser(){
       return userService.getAllUsers();
     }

     // to generate token for reset password 
     @PostMapping("/generate/reset/password/token/{email}")
      public ResponseEntity<?> generatePasswordResetToken(@PathVariable("email") String email) throws Exception{
           System.out.println(email);
          User user=generateToken.generateTokenForResetPassword(email);
          return ResponseEntity.ok(user.getId());
      }

      // to reset password 
      @PostMapping("/user/reset/password")
      public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordModel resetPasswordModel) throws Exception{
          return userService.resetPassword(resetPasswordModel);
      }

        // send notification to the owner
      @PostMapping("/send/notification/to/owner/{userId}/{ownerId}/{petId}")
      public ResponseEntity<?> sendNotification (
        @PathVariable("userId") String userId,
        @PathVariable("ownerId")String ownerId,
        @PathVariable("petId") String petId) throws Exception{
          return userService.sendNotificationToOwner(userId,ownerId,petId);
        }


        // get all notification of a user

        @GetMapping("/get/all/notification/{userId}")
         List<Notification> getAllNotification(@PathVariable("userId") String userId){
          return userService.getAllNotification(userId);
         }
     private String applicationUrl(HttpServletRequest request) {
      return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
     
    
   
   


}
