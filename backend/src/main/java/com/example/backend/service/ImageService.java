package com.example.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.model.Image;
import com.example.backend.model.User;
import com.example.backend.repository.ImageRepository;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;

    public void uploadImage(byte[] img, String description, User user) {
        imageRepository.save(new Image(img, description, user));
    }

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
