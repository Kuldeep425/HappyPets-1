package com.example.backend.backend.utils;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.backend.Reposistory.TokenRepo;
import com.example.backend.backend.Reposistory.UserRepo;
import com.example.backend.backend.collections.*;

@Service
public class GenerateToken {
   
   @Autowired
   private MailSending mailSending;
    
    @Autowired
    private TokenRepo tokenRepo;

    @Autowired UserRepo userRepo;

public String generateToekn(User user,String tokenType,String url){
    String token=UUID.randomUUID().toString();
    String message="Hi, "+user.getName()+"\n";
    Token tk=new Token(user.getId(),token,tokenType);
    tokenRepo.save(tk);
    if(tokenType.equalsIgnoreCase("Register")){
         message+="please click following link to verify your account.\n"+url+"/verifyRegistration?token="+token;
         mailSending.sendSimpleMail(user, message);
    }
  return "";
}

public User generateTokenForResetPassword(String email) throws Exception{
      if(userRepo.findByEmail(email).isEmpty() )throw new Exception("user not found");    
     User user=userRepo.findByEmail(email).get();  
      Token t=tokenRepo.findByEmail(email);
      if(t!=null) tokenRepo.delete(t);
      String token=UUID.randomUUID().toString();
      int otp=new Random().nextInt(900000)+100000;
      Token tk=new Token(user.getId(),otp,"Reset Password",email,5);
      String message="Hi, "+user.getName()+"\n";
      tokenRepo.save(tk);
      message+="Please enter this otp in order to change your password, This otp is valid for 5 min\n";
      message+="Otp : "+otp;
      mailSending.sendSimpleMail(user, message);
      return user;
}
}