package com.example.backend.backend.Reposistory;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.backend.backend.collections.User;

public interface UserRepo extends MongoRepository<User,String>{

    Optional<User> findByEmail(String email);
    
}
