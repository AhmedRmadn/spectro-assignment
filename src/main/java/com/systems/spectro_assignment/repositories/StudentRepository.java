package com.systems.spectro_assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systems.spectro_assignment.models.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	boolean existsByEmail(String email);
}