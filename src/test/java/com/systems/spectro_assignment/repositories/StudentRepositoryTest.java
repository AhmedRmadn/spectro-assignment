package com.systems.spectro_assignment.repositories;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.systems.spectro_assignment.models.Student;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class StudentRepositoryTest {

    
    private StudentRepository studentRepository;

    @Autowired
    public StudentRepositoryTest(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}


	@Test
    void studentRepository_save_shouldPersistStudent() {
        Student student = getStudent();

        Student saved = studentRepository.save(student);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("ahmed@gmail.com");
    }


    @Test
    void studentRepository_findById_shouldReturnStudent_whenExists() {
        Student student = getStudent();

        Student saved = studentRepository.save(student);

        Optional<Student> found = studentRepository.findById(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("ahmed@gmail.com");
    }


    @Test
    void studentRepository_save_shouldUpdateStudent_whenAlreadyExists() {
        Student student = getStudent();

        Student saved = studentRepository.save(student);

        saved.setFirstName("hassan");
        Student updated = studentRepository.save(saved);

        assertThat(updated.getFirstName()).isEqualTo("hassan");
    }


    @Test
    void studentRepository_delete_shouldRemoveStudent() {
        Student student = getStudent();

        Student saved = studentRepository.save(student);

        studentRepository.delete(saved);

        Optional<Student> found = studentRepository.findById(saved.getId());
        assertThat(found).isNotPresent();
    }

  
    @Test
    void studentRepository_existsByEmail_shouldReturnTrue_whenEmailExists() {
        Student student = getStudent();
        student.setDateOfBirth(LocalDate.of(2000, 9, 17));

        studentRepository.save(student);

        boolean exists = studentRepository.existsByEmail("ahmed@gmail.com");

        assertThat(exists).isTrue();
    }

    @Test
    void studentRepository_existsByEmail_shouldReturnFalse_whenEmailDoesNotExist() {
        boolean exists = studentRepository.existsByEmail("notfound@example.com");

        assertThat(exists).isFalse();
    }
    
    private Student getStudent() {
        Student student = new Student();
        student.setFirstName("ahmed");
        student.setLastName("ramadan");
        student.setEmail("ahmed@gmail.com");
        student.setDateOfBirth(LocalDate.of(2000, 9, 17));
        return student;
    }
}
