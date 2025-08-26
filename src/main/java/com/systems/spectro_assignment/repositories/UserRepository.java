package com.systems.spectro_assignment.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.systems.spectro_assignment.models.User;


public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}