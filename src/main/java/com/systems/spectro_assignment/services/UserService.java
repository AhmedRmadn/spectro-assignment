package com.systems.spectro_assignment.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.systems.spectro_assignment.exceptions.CustomAuthenticationException;
import com.systems.spectro_assignment.models.Role;
import com.systems.spectro_assignment.models.User;
import com.systems.spectro_assignment.repositories.RoleRepository;
import com.systems.spectro_assignment.repositories.UserRepository;
import com.systems.spectro_assignment.requests.CreateUserRequest;
import com.systems.spectro_assignment.requests.LoginRequest;
import com.systems.spectro_assignment.responses.TokenResponse;
import com.systems.spectro_assignment.security.JwtUtil;


@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Autowired
	public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
			JwtUtil jwtService) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtService;
	}

	public TokenResponse login(LoginRequest request) {
		Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
		if (userOpt.isEmpty()) {
			throw new CustomAuthenticationException("user not found", HttpStatus.NOT_FOUND);
		}

		User user = userOpt.get();

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new CustomAuthenticationException("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}

		String token = jwtUtil.generateToken(user);

		return new TokenResponse(token);
	}

	public TokenResponse createUser(CreateUserRequest request) {
		if (userRepository.findByUsername(request.getUsername()).isPresent()) {
			throw new CustomAuthenticationException("Username is already taken", HttpStatus.CONFLICT);
		}

		User user = new User();
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		Set<Role> roles = request.getRoles().stream()
				.map(roleName -> roleRepository.findByName(roleName).orElseThrow(
						() -> new CustomAuthenticationException("Invalid role: " + roleName, HttpStatus.BAD_REQUEST)))
				.collect(Collectors.toSet());
		user.setRoles(roles);

		userRepository.save(user);

		String token = jwtUtil.generateToken(user);

		return new TokenResponse(token);
	}
}
