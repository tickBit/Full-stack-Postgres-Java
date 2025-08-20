package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.dto.LoginRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public User signup(User userData) {
		userData.setPassword(passwordEncoder.encode(userData.getPassword()));
		
		return userRepository.save(userData);
	}
	
	public User loginUser(LoginRequest loginDto) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		
		return userRepository.findByEmail(loginDto.getEmail())
				.orElseThrow();
	}
	
    public Iterable<User> getAllUsers() {
		return userRepository.findAll();		
	}
}