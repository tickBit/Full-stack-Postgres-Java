package com.example.backend.service;

import com.example.backend.model.Image;
import com.example.backend.model.User;
import com.example.backend.repository.ImageRepository;
import com.example.backend.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private UserRepository userRepository;

    private ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void uploadImage(byte[] img, String description, User user) {
        Image image = new Image(img, description, user);
        imageRepository.save(image);
    }

    public List<Image> getUserPics(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        return imageRepository.findByUserId(user.getId());
    }

}
