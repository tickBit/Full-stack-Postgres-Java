package com.example.backend.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.model.User;
import com.example.backend.service.ImageService;
import com.example.backend.service.UserService;

@RestController
public class ImageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @PostMapping("/api/upload")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<?> uploadImg(@RequestParam("file") MultipartFile file,
                                   @RequestParam("description") String description, Principal p) throws java.io.IOException {
    
        String username = p.getName();
        User user = userService.getUser(username);
        byte[] img = file.getBytes();
        
        imageService.uploadImage(img, description, user.getId());

        return ResponseEntity.ok().body("Image uploaded!");
    }

}
