package com.example.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;

	private String description;
    
    @Column(name = "file_data", columnDefinition = "bytea")
    private byte[] image;

    @JoinColumn(name = "userid", nullable = false)
    private Long uploaderId;

    public Long getUploader() {
        return this.uploaderId;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setUploader(Long uploaderId) {
        this.uploaderId = uploaderId;
    }

    public Image(byte[] img, String description, Long uploaderId) {
        this.description = description;
        this.image = img;
        this.uploaderId = uploaderId;
    }
}
