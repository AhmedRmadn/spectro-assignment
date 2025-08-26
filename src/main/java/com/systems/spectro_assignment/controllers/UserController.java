package com.systems.spectro_assignment.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.systems.spectro_assignment.requests.CreateUserRequest;
import com.systems.spectro_assignment.requests.LoginRequest;
import com.systems.spectro_assignment.responses.ApiResponse;
import com.systems.spectro_assignment.responses.TokenResponse;
import com.systems.spectro_assignment.services.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class UserController {
	
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<TokenResponse>> register(@Valid @RequestBody CreateUserRequest request) {
    	TokenResponse data = userService.createUser(request);
    	ApiResponse<TokenResponse> response = new ApiResponse<TokenResponse>();
    	response.setData(data);
    	response.setSuccess(true);
    	response.setMessage("User registered successfully");
    	return new ResponseEntity<>(response, HttpStatus.CREATED);    
    	}

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponse>> login(@Valid @RequestBody LoginRequest request) {
    	TokenResponse data = userService.login(request);
    	ApiResponse<TokenResponse> response = new ApiResponse<TokenResponse>();
    	response.setData(data);
    	response.setSuccess(true);
    	response.setMessage("Login successful");
    	return new ResponseEntity<>(response, HttpStatus.CREATED);  
    }



}