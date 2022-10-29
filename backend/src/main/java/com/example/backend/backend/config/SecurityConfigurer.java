package com.example.backend.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class SecurityConfigurer {
    private static final String[] WHITE_LIST={"/register/user",};

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

   @Bean
   SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
          .cors()
          .and()
          .csrf()
          .disable()
          .authorizeHttpRequests()
          .antMatchers(WHITE_LIST)
          .permitAll();
          return http.build();
    }
}
