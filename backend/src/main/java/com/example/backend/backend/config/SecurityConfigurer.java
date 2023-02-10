package com.example.backend.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.backend.backend.security.JwtAuthenticationEntryPoint;
import com.example.backend.backend.security.JwtAuthenticationFilter;
import com.example.backend.backend.service.JwtUserDetail;
import com.google.protobuf.Method;


@Configuration
@EnableWebSecurity

public class SecurityConfigurer {
    @Autowired private JwtUserDetail jwtUserDetail;
    @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

   @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf()
          .disable()
          .authorizeHttpRequests()
          .antMatchers("/login/user","/register/user","/verifyRegistration",
          "/generate/reset/password/**","/user/reset/password")
          .permitAll()
          .anyRequest()
          .authenticated()
          .and()
          .exceptionHandling()
          .authenticationEntryPoint(jwtAuthenticationEntryPoint)
          .and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and()
          .authenticationProvider(daoAuthenticationProvider())
          .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
           return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
         DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
         provider.setUserDetailsService(jwtUserDetail);
         provider.setPasswordEncoder(passwordEncoder());
         return provider;
    }
    
    @Bean 
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception{
           return configuration.getAuthenticationManager();     
         }

}
