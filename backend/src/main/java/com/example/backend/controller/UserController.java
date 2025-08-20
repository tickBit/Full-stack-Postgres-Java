package com.example.backend.controller;
import com.example.backend.service.JwtService;
import com.example.backend.service.UserService;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.example.backend.dto.RegisterRequest;
import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.response.LoginResponse;
import com.example.backend.dto.LoginRequest;

@RestController
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/api/login")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginDto) {

		User user = userService.loginUser(loginDto);
		
		String jwtToken = jwtService.generateToken(new HashMap<>(), user);
		
		LoginResponse loginResponse = new LoginResponse();
		
		loginResponse.setToken(jwtToken);
		loginResponse.setTokenExpireTime(jwtService.getExpirationTime());
		
		return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/api/register")
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    public ResponseEntity<User> postMethodName(@RequestBody User user) {
		
		User user2 = userService.signup(user);
		
		return ResponseEntity.ok(user2);
		
	}

    @GetMapping("/api/deleteme")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> deleteMe() {
        
        return ResponseEntity.ok().body("User deleted");
    }
    
    
}