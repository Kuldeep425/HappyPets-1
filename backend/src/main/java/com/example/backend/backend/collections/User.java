package com.example.backend.backend.collections;

import java.util.List;

import org.bson.types.Binary;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String  imageURL;
    private String dob;
    private String address;
    private String pincode;
    private List<String>postedPetId;
    private List<String>favouritePetId;
    private boolean hasLoggedIn=false;
    private int profileCompleted=0;
    private boolean verified=false;
    private int postedPet;
    private int favouritePet;
}
