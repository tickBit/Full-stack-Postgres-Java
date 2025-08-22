package com.example.backend.controller;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.LoginRequest;
import com.example.backend.model.User;
import com.example.backend.response.LoginResponse;
import com.example.backend.service.JwtService;
import com.example.backend.service.UserService;

@RestController
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/auth/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginDto) {
    
		User user = userService.loginUser(loginDto);
		
    String jwtToken = jwtService.generateToken(user);
		
		LoginResponse loginResponse = new LoginResponse();
		
		loginResponse.setToken(jwtToken);
		
		return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/auth/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> registerUser(@RequestBody User user, Principal p) {
		
      if (p != null) {
        return ResponseEntity.status(401).body("Please log out first");
      }
		  
      String username = user.getUsername();
      if (userService.userExists(username)) return ResponseEntity.status(401).body("User exists by that name");
      
      User user2 = userService.signup(user);
		
		  return ResponseEntity.ok(user2);
		
	}

    @GetMapping("/api/deleteme")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> deleteMe(Principal principal) {
        
        // get logged in user
        String username = principal.getName();
        userService.deleteUser(username);
        return ResponseEntity.ok().body("User deleted");
    }
    
    
}