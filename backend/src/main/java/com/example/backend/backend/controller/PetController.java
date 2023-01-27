package com.example.backend.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.backend.collections.Pet;
import com.example.backend.backend.service.PetService;

@RestController
public class PetController {
    
    @Autowired
    private PetService petService;
    

    // to post a pet 
    @PostMapping("/post/pet/{userId}")
    public String postAPost(@PathVariable String userId,
    @RequestPart ("image") MultipartFile file,
    @RequestPart ("pet") Pet pet){
      System.out.println("api is called");
      System.out.println(pet.getName());
     return petService.postAPet(userId,pet,file);
    }

    // to get all posted pet of specific user
    @GetMapping("/get/all/posted/pet/{userId}")
    public ResponseEntity<?> getAllPostedPetOfASpecificUser(@PathVariable String userId){
       return ResponseEntity.ok(petService.getAllpostedPetOfASpecificUser(userId));
    }

    // get all posted pets
    @GetMapping("/get/all/posted/pets")
    public ResponseEntity<?> getAllPostedPets(){
      System.out.println("called");
      return ResponseEntity.ok(petService.getAllpostedPets());
    }

    HashMap<Integer,String>map=new HashMap<>();
     
    // to get pet on the basis of filter
    @GetMapping("get/pets/category/{num}")
    public ResponseEntity<?> getPetOnBasisOfCategory(@PathVariable("num") int num){
       map.put(0,"Dog");
       map.put(1,"Cat");
       map.put(2,"Horse");
       map.put(3,"Bird");
       map.put(4,"Fish");
       map.put(5,"Rabbit");
       String category=map.get(num);
       return ResponseEntity.ok(petService.getPostedPetOnBasisOfCategory(category));
    }

    // get latest posted pet
    @GetMapping("/get/recently-posted/pets")
    public List<Pet> getNewestPosted(){
         return petService.getNewestPosted();
    }
    
    // to get the pet by id

    @GetMapping("/get/specific/pet/{petId}")
    public Pet getSpecificPet(@PathVariable("petId") String petId){
         return petService.getSpecificPet(petId);
    }
  
}
