package com.example.backend.repository;


import com.example.backend.model.Image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
    List<Image> findByUserId(Long userid);


}