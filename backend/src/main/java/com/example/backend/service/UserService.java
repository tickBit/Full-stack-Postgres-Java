package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.dto.LoginRequest;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;


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
	
	public boolean userExists(String username) {
		
		if ((userRepository.findByUsername(username)).isPresent()) return true; else return false;
	}

	public User loginUser(LoginRequest loginDto) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
		
		return userRepository.findByUsername(loginDto.getUsername())
				.orElseThrow();
	}
	
    public void deleteUser(String username) {

        userRepository.deleteByUsernameNative(username);
    }

	// get particular user
	public User getUser(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

    public Iterable<User> getAllUsers() {
		return userRepository.findAll();		
	}

	public Authentication authenticate(String username, String password) {
		try {
			Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
			);
			return auth;
		} catch (Exception e) {
			return null;
		}
	}
}