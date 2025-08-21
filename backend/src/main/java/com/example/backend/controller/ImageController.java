package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.service.ImageService;
import com.example.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // frontend URL
public class ImageController {

    private final ImageService imageService;
    private final UserService userService;

    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImg(
            @RequestParam("file") MultipartFile file,
            @RequestParam("description") String description,
            Principal principal
    ) throws IOException {

        String username = principal.getName();
        User user = userService.getUser(username);

        imageService.uploadImage(file.getBytes(), description, user);

        return ResponseEntity.ok("Upload successful");
    }
}
