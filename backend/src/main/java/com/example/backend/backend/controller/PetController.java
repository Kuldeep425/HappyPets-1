package com.example.backend.backend.controller;

import java.io.IOException;
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
    public ResponseEntity<?> postAPost(@PathVariable String userId,
    @RequestPart ("image") MultipartFile file,
    @RequestPart ("pet") Pet pet) throws IOException{
      System.out.println("api is called");
      Pet p=petService.postAPet(userId, pet, file);
      if(p==null) return ResponseEntity.ok("error");
     return ResponseEntity.ok(p.getId());
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

    //add to favourite 
    @PostMapping("/add/to/favourite/{userId}/{petId}")
    public ResponseEntity<?> addToFavourite(@PathVariable("userId") String userId, @PathVariable("petId") String petId){
        Pet p=petService.addToFavourite(userId,petId);
        if(p==null)return null;
        return ResponseEntity.ok("Added");
    }

    //remove from favourite list
    @PostMapping("/remove/from/favourite/list/{userId}/{petId}")
    public ResponseEntity<?> removeFromFavourite(@PathVariable("userId") String userId, @PathVariable("petId") String petId){
      Pet p=petService.removeFromFavourite(userId,petId);
      if(p==null) return null;
      return ResponseEntity.ok("Removed");
  }

    //get all favourite pets of a specific user
    @GetMapping("/get/all/favourite/pets/{userId}")
    public List<Pet> getAllFavouritePets(@PathVariable("userId") String userId){
        return petService.getAllFovouritePets(userId);
    }

    //get a specific pet based on PetId

    @GetMapping("/get/specific/pet/{userId}/{petId}")
    public Pet getSpecificPet(@PathVariable("userId") String userId,@PathVariable("petId") String petId){
        Pet p=petService.getSpecificPet(userId,petId);
        return p;
    }

}
