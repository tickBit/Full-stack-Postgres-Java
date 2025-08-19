package com.example.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.backend.dto.RegisterRequest;
import com.example.backend.dto.LoginRequest;

@RestController
public class UserController {

    @PostMapping("/api/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest entity) {

        System.out.println(entity.getUsername());
        System.out.println(entity.getPassword());

        return ResponseEntity.ok().body("User logged in");
    }

    @PostMapping("/api/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest entity) {
        
        System.out.println(entity.getUsername());
        System.out.println(entity.getPassword());
        System.out.println(entity.getEmail());
        
        return ResponseEntity.ok().body("User registered");
    }

    @GetMapping("/api/deleteme")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> deleteMe() {
        
        return ResponseEntity.ok().body("User deleted");
    }
    
    
}