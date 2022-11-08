package com.example.backend.backend.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.backend.Reposistory.PetRepo;
import com.example.backend.backend.Reposistory.UserRepo;
import com.example.backend.backend.collections.*;
import com.example.backend.backend.utils.DataBucketUtil;


@Service
public class PetServiceImpl implements PetService {

     @Autowired 
     private UserRepo userRepo;
     @Autowired
      private PetRepo petRepo;
     @Autowired
      private DataBucketUtil dataBucketUtil;

    // post a pet to adopt 
    @Override
    public ResponseEntity<?> postAPet(String userId, Pet pet, MultipartFile file) {
      Optional<User>user=userRepo.findById(userId);
      if(user.isEmpty())
        return ResponseEntity.ok("user not found");
        pet.setOwnerId(userId);
        String originalFileName=file.getOriginalFilename();
        if(originalFileName==null){
           return ResponseEntity.ok("original file name is null");
         }
        Path path=new File(originalFileName).toPath();
        String url=null;
         try {
          String contentType=Files.probeContentType(path);
           try {
            url=dataBucketUtil.uploadFile(file,originalFileName,contentType);
          } catch (Exception e) {
            e.printStackTrace();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
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
        return ResponseEntity.ok("Pet posted successfully");
    }

   // get all posted pets of a specific user
    @Override
    public ResponseEntity<?> getAllpostedPetOfASpecificUser(String userId) {
        if(userRepo.findById(userId).isEmpty()) return ResponseEntity.ok("user not found");
        return ResponseEntity.ok(petRepo.findAllByUserId(userId));
    }


    // get all posted pets
    @Override
    public List<Pet> getAllpostedPets(){
      return petRepo.findAll();
    }

  



    
}
