package com.example.backend.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.backend.backend.Model.LoginModel;
import com.example.backend.backend.Model.LoginResponse;
import com.example.backend.backend.Model.ResetPasswordModel;
import com.example.backend.backend.Reposistory.TokenRepo;
import com.example.backend.backend.Reposistory.UserRepo;
import com.example.backend.backend.collections.Token;
import com.example.backend.backend.collections.User;
import com.example.backend.backend.config.SecurityConfigurer;
import com.example.backend.backend.utils.JwtUtil;
import com.example.backend.backend.utils.Utils;
import com.google.gson.JsonObject;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserRepo userRepo;
    @Autowired private SecurityConfigurer securityConfigurer;
    @Autowired private TokenRepo tokenRepo;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private JwtUserDetail jwtUserDetail;
    @Autowired private Utils utils;

    // register a user 
    @Override
    public User registerUser(User user) {
        user.setPassword(securityConfigurer.passwordEncoder().encode(user.getPassword()));
        if(userRepo.findByEmail(user.getEmail()).isPresent()){
            User user1=userRepo.findByEmail(user.getEmail()).get();
            if(user1.isVerified()) return null;
        }
        User user1=userRepo.save(user);
        return user1;
    }

    
   // to find a user based on user id
   @Override
   public User getUserByUserId(String user_id) {
       User user=userRepo.findById(user_id).get();
       if(user!=null){
           user.setFavouritePet(0);
           user.setPostedPet(0);
           if(user.getFavouritePetId()!=null) user.setFavouritePet(user.getFavouritePetId().size());
           if(user.getPostedPetId()!=null) user.setPostedPet(user.getPostedPetId().size());
       }
       return user;
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
   
   // reset password method 
  @Override
  public ResponseEntity<?> resetPassword(ResetPasswordModel resetPasswordModel) throws Exception {
         if(resetPasswordModel==null) throw new Exception("Body is null");
             int otp=resetPasswordModel.getOtp();
             String newPassword=resetPasswordModel.getNewPassword();
             String email=resetPasswordModel.getEmail();
             if(otp==0 || newPassword==null || email==null) throw new Exception("Reset token or newPassword is null");
             Token tkn=tokenRepo.findByEmail(email);
             if(tkn==null) throw new Exception("Token is not right");
             User user=userRepo.findById(tkn.getUserId()).get();
             if(user==null) throw new Exception("user not found");
             Calendar calendar=Calendar.getInstance();
             if(tkn.getExpirationTime().getTime()-calendar.getTime().getTime()<=0){
                tokenRepo.delete(tkn);
                    throw new Exception("Token expired");   
               }
             if(tkn.getOtp()!=otp) throw new Exception("Otp does not match");
              user.setPassword(securityConfigurer.passwordEncoder().encode(newPassword));
              userRepo.save(user);
              tokenRepo.delete(tkn);
              return ResponseEntity.ok(resetPasswordModel);
  }

   // to login user
   @Override
   public ResponseEntity<?> loginUser(LoginModel loginModel) throws Exception {
        if(loginModel==null || loginModel.getEmail()==null || loginModel.getPassword()==null) return ResponseEntity.ok("No credential found");
        try {
           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
        } 
        catch (BadCredentialsException e){
          throw new Exception("Invalid credentials");
        }
        catch (Exception e) {
           System.out.println(e);
        }
        UserDetails userDetail=jwtUserDetail.loadUserByUsername(loginModel.getEmail());
        User user=userRepo.findByEmailAndPassword(userDetail.getUsername(),userDetail.getPassword());
        if(user.isVerified()==false) throw new Exception("Account is not verified");
        String  token=jwtUtil.generateToken(jwtUserDetail.loadUserByUsername(loginModel.getEmail()));
        System.out.println(token);
       LoginResponse loginResponse=new LoginResponse(user.getId(),token,user.getProfileCompleted());
        return ResponseEntity.ok(loginResponse); 
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

  // to find all users
  @Override
  public List<User> getAllUsers() {
     return userRepo.findAll();
  }



  // update user
  @Override
  public User updateUser(User user, MultipartFile file){
          if(userRepo.findById(user.getId()).isEmpty()) return null;
          
          Cloudinary cloudinary=utils.getCloudinary();
          File profFile=null;
          try {
             profFile=utils.convertMultiPartToFile(file);
          } catch (IOException e) {
            e.printStackTrace();
          }
          Map uploadResponse;
          try {
            uploadResponse=cloudinary.uploader().upload(profFile, ObjectUtils.emptyMap());
          } catch (IOException e) {
               return null;
          }
          User user1=userRepo.findById(user.getId()).get();
          user1.setName(user.getName());
          user1.setPhoneNumber(user.getPhoneNumber());
          String url=(String) uploadResponse.get("url");
          user1.setImageURL(url);
          user1.setAddress(user.getAddress());
          user1.setPincode(user.getPincode());
          user1.setDob(user.getDob());
          user1.setProfileCompleted(1);
          User u=userRepo.save(user1);
          System.out.println(url);
          return u;
        }



}
