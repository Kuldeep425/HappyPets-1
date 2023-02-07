package com.example.backend.backend.security;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.backend.backend.service.JwtUserDetail;
import com.example.backend.backend.utils.JwtUtil;
import com.mongodb.lang.Nullable;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Autowired JwtUserDetail jwtUserDetail;
    @Autowired JwtUtil jwtUtil;

    private static List<String> skipFilterUrls = Arrays.asList("/login/user","/register/user","/verifyRegistration"
                                                ,"/user/reset/password","/generate/reset/password/token/**");
    @Override
   protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

   return skipFilterUrls.stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
}


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException{
            String requestToken=request.getHeader("Authorization");
            System.out.println("Token created");
            System.out.println(request);
            System.out.println(requestToken);
            if(requestToken==null) return;
            String email=null;
            String token=null;
            if(request!=null && requestToken.startsWith("Bearer ")){
              token=requestToken.substring(7);
              try{
              email=jwtUtil.extractUsername(token);
              }
              catch(IllegalArgumentException e){
                  System.out.println(e);
                  System.out.println("Unable to get jwt token");
              }
              catch(ExpiredJwtException e){
                 System.out.println(e);
                 System.out.println("Jwt token has expired");
              }
              catch(MalformedJwtException e){
                System.out.println("Malformed expection");
              }
            }
            else{
                System.out.println("Token does not begin with Bearer");
            }

            // once we get the token 
            if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails user=jwtUserDetail.loadUserByUsername(email);
                if(jwtUtil.validateToken(token, user)){
                      // token is valid 
                      // let authenticate what the token contains
                      UsernamePasswordAuthenticationToken userAuthenticationToken=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                      userAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                      SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
                    }
            }
            else{
                System.out.println("Jwt token does not has email");
            }
            filterChain.doFilter(request, response);       
    }
    
}
