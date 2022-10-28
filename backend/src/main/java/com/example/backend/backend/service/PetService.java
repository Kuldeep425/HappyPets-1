package com.example.backend.backend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.backend.collections.Pet;

public interface PetService {

    ResponseEntity<?> postAPet(String userId, Pet pet, MultipartFile file);

    ResponseEntity<?> getAllpostedPetOfASpecificUser(String userId);

    Object getAllpostedPets();
    
}
