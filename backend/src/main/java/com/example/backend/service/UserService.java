package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
	
	// signed in user can delete him/herself
	public void deleteUser(String username) {
		User user = userRepository.findByUsername(username).orElse(null);

		userRepository.delete(user);
	}

    public Iterable<User> getAllUsers() {
		return userRepository.findAll();		
	}
}