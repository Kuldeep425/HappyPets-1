package com.example.backend.backend.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.backend.Reposistory.UserRepo;
import com.example.backend.backend.collections.User;

@Service
public class JwtUserDetail implements UserDetailsService{
    @Autowired private UserRepo userRepo;
    @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     User user=userRepo.findByEmail(email).get();
     if(user==null || user.isVerified()==false) new Exception("not find the user associated to this email");
     return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),new ArrayList<>());
  }   
}
