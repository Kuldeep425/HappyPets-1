package com.example.backend.backend.Reposistory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;

import com.example.backend.backend.collections.Pet;

public interface PetRepo extends MongoRepository<Pet,String>{
    
    @Query("{ownerId:?0}")
    ResponseEntity<?> findAllByUserId(String userId);
    
}
