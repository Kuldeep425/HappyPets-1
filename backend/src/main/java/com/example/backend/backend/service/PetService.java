package com.example.backend.backend.service;

import java.util.List;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.backend.collections.Pet;

public interface PetService {

    String postAPet(String userId, Pet pet, MultipartFile file);

    List<Pet> getAllpostedPetOfASpecificUser(String userId);

    Object getAllpostedPets();

    
    List<Pet> getPostedPetOnBasisOfCategory(String category);

    List<Pet> getNewestPosted();

    Pet getSpecificPet (String petId);
    
}
