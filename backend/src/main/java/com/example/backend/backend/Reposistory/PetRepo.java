package com.example.backend.backend.Reposistory;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;

import com.example.backend.backend.collections.Pet;

public interface PetRepo extends MongoRepository<Pet,String>{
    
    @Query("{ownerId:?0}")
    List<Pet> findAllByUserId(String userId);

    @Query("{category:?0}")
    List<Pet> getPostedPetOnBasisOfCategory(String category);
    
}
