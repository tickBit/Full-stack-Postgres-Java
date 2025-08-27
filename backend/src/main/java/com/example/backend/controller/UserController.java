package com.example.backend.controller;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.LoginRequest;
import com.example.backend.model.User;
import com.example.backend.response.LoginResponse;
import com.example.backend.service.ImageService;
import com.example.backend.service.JwtService;
import com.example.backend.service.UserService;

@RestController
public class UserController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageService imageService;

    @PostMapping("/auth/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginDto, Principal p) {
    
        if (p != null) {
          return ResponseEntity.status(401).body(null);
        } else {
          User user = userService.loginUser(loginDto);
          String jwtToken = jwtService.generateToken(user);
          LoginResponse loginResponse = new LoginResponse();
          loginResponse.setToken(jwtToken);
          loginResponse.setTokenExpireTime(jwtService.getExpirationTime());
          return ResponseEntity.ok(loginResponse);
        }
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
		
      // get token immediately after registration
      String jwtToken = jwtService.generateToken(user2);
      LoginResponse loginResponse = new LoginResponse();
      loginResponse.setToken(jwtToken);
		  return ResponseEntity.ok(jwtToken);
		
	}

  @DeleteMapping("/api/deleteme")
  @CrossOrigin(origins = "http://localhost:3000")
  // authenticated users only
  public ResponseEntity<Void> deleteOwnAccount(Authentication authentication) {

    // Hae kirjautuneen käyttäjän username
    String username = authentication.getName();

    // Poista käyttäjä palvelukerroksessa
    userService.deleteUser(username);

    return ResponseEntity.noContent().build();
  }
 
}