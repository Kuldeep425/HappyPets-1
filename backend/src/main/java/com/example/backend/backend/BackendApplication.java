package com.example.backend.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.cloudinary.*;


@SpringBootApplication
public class BackendApplication {
	public static Cloudinary cloudinary;
	public static void main(String[] args){
		SpringApplication.run(BackendApplication.class, args);
		System.out.println("server started");
	}

}
