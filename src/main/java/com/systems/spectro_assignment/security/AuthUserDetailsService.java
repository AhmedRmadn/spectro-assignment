package com.systems.spectro_assignment.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.systems.spectro_assignment.repositories.UserRepository;


@Service
public class AuthUserDetailsService implements UserDetailsService{
	 private final UserRepository userRepository;
	 
	 @Autowired
	 public AuthUserDetailsService(UserRepository userRepository) {
		 this.userRepository = userRepository;
	 }
	 
	 @Override
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	     return userRepository.findByUsername(username).orElse(null);
	 }

}
