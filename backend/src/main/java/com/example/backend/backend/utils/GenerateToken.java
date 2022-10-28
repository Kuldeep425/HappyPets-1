package com.example.backend.backend.utils;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.backend.Reposistory.TokenRepo;
import com.example.backend.backend.collections.*;

@Service
public class GenerateToken {
   
   @Autowired
   private MailSending mailSending;
    
    @Autowired
    private TokenRepo tokenRepo;

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

}