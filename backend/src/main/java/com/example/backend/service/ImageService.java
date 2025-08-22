package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.model.User;
import com.example.backend.repository.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void uploadImage(byte[] img, String description, User user) {
        Image image = new Image(img, description, user);
        imageRepository.save(image);
    }

}
