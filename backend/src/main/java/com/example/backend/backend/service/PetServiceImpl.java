package com.example.backend.backend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.checkerframework.checker.units.qual.s;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.backend.backend.Reposistory.PetRepo;
import com.example.backend.backend.Reposistory.UserRepo;
import com.example.backend.backend.collections.*;
import com.example.backend.backend.utils.Utils;


@Service
public class PetServiceImpl implements PetService {

     @Autowired 
     private UserRepo userRepo;
     @Autowired
      private PetRepo petRepo;
     @Autowired 
      private Utils utils;

    // post a pet to adopt 
    @Override
    public Pet postAPet(String userId, Pet pet, MultipartFile file){
      Optional<User>user=userRepo.findById(userId);
      System.out.println(pet.getCategory());
      if(user.isEmpty()) return null;
        pet.setOwnerId(userId);
        String url=null;
        File file1=null;
        try {
          file1 = utils.convertMultiPartToFile(file);
        } catch (IOException e) {
          e.printStackTrace();
        }
        Cloudinary cloudinary=utils.getCloudinary();
        Map uploadResponse;
        try {
          uploadResponse=cloudinary.uploader().upload(file1, ObjectUtils.emptyMap());
        } catch (IOException e) {
             return null;
        }
        url=uploadResponse.get("url").toString();
        System.out.println(url);
        pet.setImageURL(url);
        Pet p=petRepo.save(pet);
        List<String>PetId=user.get().getPostedPetId();
        User u=user.get();
        if(PetId==null){
           List<String>pid=new ArrayList<>();
           pid.add(p.getId());
           u.setPostedPetId(pid);
        }
        else
        PetId.add(p.getId());
        userRepo.save(u);
        return p;
    }

   // get all posted pets of a specific user
    @Override
    public List<Pet> getAllpostedPetOfASpecificUser(String userId) {
        if(userRepo.findById(userId).isEmpty()) return null;
        return petRepo.findAllByUserId(userId);
    }


    // get all posted pets
    @Override
    public List<Pet> getAllpostedPets(){
      return petRepo.findAll();
    }
    

    // to find pets on basis of category 
    @Override
    public List<Pet> getPostedPetOnBasisOfCategory(String category) {
      List<Pet>p=petRepo.getPostedPetOnBasisOfCategory(category);
      System.out.println(p);
      return p;
    }

    // to find the latest posted pets 
    @Override
    public List<Pet> getNewestPosted() {
         List<Pet> total=petRepo.findAll();
         List<Pet> result=new ArrayList<>();
         int cnt=10;
         if(cnt>total.size()) cnt=total.size();
         for(int i=1; i<=cnt; i++){
              result.add(total.get(total.size()-i));
         }
         return result;
    }


    // add pet in user's favourite pet list
    @Override
    public Pet addToFavourite(String userId, String petId) {
           if(userRepo.findById(userId).isEmpty()) return null;
           if(petRepo.findById(petId).isEmpty()) return null;
           User user=userRepo.findById(userId).get();
           List<String>FavPetId=user.getFavouritePetId();
           if(FavPetId==null){
              List<String>pid=new ArrayList<>();
              pid.add(petId);
              user.setFavouritePetId(pid);
           }
           else
           FavPetId.add(petId);
           userRepo.save(user);
           return petRepo.findById(petId).get(); 
    }

    // remove from favourite list
    @Override
    public Pet removeFromFavourite(String userId, String petId) {
           if(userRepo.findById(userId).isEmpty()) return null;
          //if(petRepo.findById(petId).isEmpty()) return ResponseEntity.ok("Pet not found");
           User user=userRepo.findById(userId).get();
           List<String>FavPetId=user.getFavouritePetId();
           if(FavPetId!=null)FavPetId.remove(petId); 
           user.setFavouritePetId(FavPetId); 
           userRepo.save(user);
           return petRepo.findById(petId).get();   
    }


    // get all favourite pets of a specific user
    @Override
    public List<Pet> getAllFovouritePets(String userId) {
        if(userRepo.findById(userId).isEmpty()) return null;
            List<String>petsId=userRepo.findById(userId).get().getFavouritePetId();
            if(petsId==null) return null;
            List<Pet>p=new ArrayList<>();
            for(int i=0; i<petsId.size(); i++){
                 if(petRepo.findById(petsId.get(i)).isPresent()==true) p.add(petRepo.findById(petsId.get(i)).get());
            }
            return p;
    }
    

    // get specific pet 
    @Override
    public Pet getSpecificPet(String userId, String petId) {
       User user=userRepo.findById(userId).get();
       if(user==null) return null;
       Pet pet=petRepo.findById(petId).get();
       if(pet==null) return null;
       int isFav=0;
       if(user.getFavouritePetId()!=null){
       for(int i=0; i<user.getFavouritePetId().size(); i++){
        System.out.println(petId+ " "+user.getFavouritePetId().get(i));
        if(petId.compareTo(user.getFavouritePetId().get(i))==0) isFav=1;
        if(isFav==1) break;
       }
      }
      System.out.println(isFav);
      pet.setFav(isFav);
      return pet;
    }

    //delete the pet 
    @Override
    public Pet deleteSpecificPet(String userId, String petId){
        if(userId==null || petId==null) return null;
        Pet pet=petRepo.findById(petId).get();
        User user=userRepo.findById(userId).get();
        if(pet==null || user==null) return null;
        if(pet.getOwnerId().compareTo(userId)!=0) return null;
        petRepo.deleteById(petId);
        List<String>postedPetId=user.getPostedPetId();
        if(postedPetId!=null)postedPetId.remove(petId); 
        user.setPostedPetId(postedPetId); 
        userRepo.save(user);
        return pet;
    }

  



    
}
