package com.example.backend.backend.utils;
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

public String generateTokenForResetPassword(String email){
      if(userRepo.findByEmail(email).isEmpty() || userRepo.findByEmail(email).get().isVerified()==false) return "User not found";       
      User user=userRepo.findByEmail(email).get();
      String token=UUID.randomUUID().toString();
      Token tk=new Token(user.getId(),token,"Reset Password");
      String message="Hi, "+user.getName()+"\n";
      Token t=tokenRepo.save(tk);
      message+="Please enter this token in order to change your password, This token is valid for 10 min\n";
      message+="Token : "+t.getId();
      mailSending.sendSimpleMail(user, message);
      return "Email sent successfully";
}
}