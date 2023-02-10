package com.example.backend.backend.Reposistory;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.backend.backend.collections.Token;

public interface TokenRepo extends MongoRepository<Token,String>{

    Token findByToken(String token);
    Token findByEmail(String email);
    
}
