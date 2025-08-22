package com.example.backend.controller;

import com.example.backend.model.Image;
import com.example.backend.model.User;
import com.example.backend.service.ImageService;
import com.example.backend.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // frontend URL
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;

    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImg(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            Principal principal
    ) throws IOException {

        String username = principal.getName();
        User user = userService.getUser(username);

        Image img = imageService.uploadImage(file.getBytes(), description, user);

        return ResponseEntity.ok(img);
    }

    @GetMapping("/getUserPics")
    public List<Image> getUserPics(Principal p) {
        String username = p.getName();
        return imageService.getUserPics(username);
    }
    
    @DeleteMapping("/deletePic/{id}")
    public void deletepic(@PathVariable("id") Long id) {
        
        imageService.deletePicById(id);
    }
}
