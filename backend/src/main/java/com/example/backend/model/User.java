package com.example.backend.model;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
	
	
	   @Id
	   @GeneratedValue(strategy = GenerationType.AUTO)
	   @Column(nullable = false)
	   private Integer id;

	   private String username;
	   private String email;
	   private String password;
	   
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}
	
	@Override
	public String getPassword() {
		
		return password;
	}
	
	@Override
	public String getUsername() {
		
		return email;
	}
	
	  @Override
	   public boolean isAccountNonExpired() {
	       return true;
	   }
	  
	   @Override
	   public boolean isAccountNonLocked() {
	       return true;
	   }
	   
	   @Override
	   public boolean isCredentialsNonExpired() {
	       return true;
	   }
	   
	   @Override
	   public boolean isEnabled() {
	       return true;
	   }

	
	public User(Integer id, String username, String email, String password) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public User() {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return username;
	}
	public void setUsername(String name) {
		this.username = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}