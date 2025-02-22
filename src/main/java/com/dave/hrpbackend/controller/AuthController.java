package com.dave.hrpbackend.controller;

import com.dave.hrpbackend.entity.dto.LoginRequest;
import com.dave.hrpbackend.security.CustomUserDetails;
import com.dave.hrpbackend.utils.JwtTools;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private static final String BEARER_PREFIX = "Bearer ";


    @Autowired
    private JwtTools jwtTools;

    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        Authentication token = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        Authentication auth = authenticationManager.authenticate(token);
        return jwtTools.createLoginAccessToken((CustomUserDetails) auth.getPrincipal());
    }
}
