package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.model.Image;
import com.example.backend.repository.ImageRepository;

@Service
public class ImageService {
    
    @Autowired
    private ImageRepository imageRepository;

    public void uploadImage(byte[] img, String description, Long id) {
        imageRepository.save(new Image(img, description, id));
        return;
    }
}
