package com.example.backend.backend.collections;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="Pet")
public class Pet {
    @Id
    private String id;
    private String name;
    private String breed;
    private String gender;
    private String age;
    private String category;
    private String imageURL;
    private String ownerId;
    
}
