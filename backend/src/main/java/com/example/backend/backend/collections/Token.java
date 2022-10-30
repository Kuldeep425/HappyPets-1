package com.example.backend.backend.collections;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@NoArgsConstructor
@Document(collection = "Token")
public class Token {
    
//expirationTime 10min
private static final int EXPIRATION_TIME=10;
@Id
private String  id;
private String token;
private Date expirationTime;
private String tokenType;
private String userId;


public Token(String userId, String token,String tokenType){
    super();

    this.userId=userId;
    this.token=token;
    this.tokenType=tokenType;
    this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
}

public Token(String token){
    this.token=token;
    this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
}

private Date calculateExpirationDate(int expirationTime) {
    Calendar calendar=Calendar.getInstance();
    calendar.setTimeInMillis(new Date().getTime());
    calendar.add(calendar.MINUTE, expirationTime);
    return new Date(calendar.getTime().getTime());
}


}
