package com.example.backend.model;

import jakarta.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false)
	private Long id;

    @Column(unique = true, nullable = false)
	private String username;

    @Column(nullable = false)
	private String password;
	
    private String email;

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
		
		return username;
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

	
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public User() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
    public void setUsername(String username) {
        this.username = username;
    }

	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}