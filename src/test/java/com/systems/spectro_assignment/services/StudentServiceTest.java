package com.systems.spectro_assignment.services;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;

import com.systems.spectro_assignment.exceptions.StudentException;
import com.systems.spectro_assignment.models.Student;
import com.systems.spectro_assignment.repositories.StudentRepository;
import com.systems.spectro_assignment.requests.StudentRequest;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void studentService_createStudent_success() {
        StudentRequest request = new StudentRequest();
        request.setFirstName("ahmed");
        request.setLastName("ramadan");
        request.setEmail("ahmed@gmail.com");
        request.setDateOfBirth(LocalDate.of(2000, 9, 17));

        when(studentRepository.existsByEmail("ahmed@gmail.com")).thenReturn(false);

        Student savedStudent = new Student(1L, "ahmed", "ramadan", "ahmed@gmail.com", LocalDate.of(2000, 9, 17));
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        Student result = studentService.createStudent(request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("ahmed@gmail.com");

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void studentService_createStudent_duplicateEmail_shouldThrowException() {
        StudentRequest request = new StudentRequest();
        request.setFirstName("ahmed");
        request.setLastName("ramadan");
        request.setEmail("ahmed@gmail.com");
        request.setDateOfBirth(LocalDate.of(2000, 9, 17));

        when(studentRepository.existsByEmail("ahmed@gmail.com")).thenReturn(true);

        StudentException ex = assertThrows(StudentException.class,
                () -> studentService.createStudent(request));

        assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void studentService_getStudentById_success() {
        Student student = new Student(1L, "ahmed", "ramadan", "ahmed@gmail.com", LocalDate.of(2000, 9, 17));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student result = studentService.getStudentById(1L);

        assertThat(result.getFirstName()).isEqualTo("ahmed");
    }

    @Test
    void studentService_getStudentById_notFound_shouldThrowException() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        StudentException ex = assertThrows(StudentException.class,
                () -> studentService.getStudentById(99L));

        assertThat(ex.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void studentService_updateStudent_success() {
        Student existing = new Student(1L, "ahmed", "ramadan", "ahmed@gmail.com", LocalDate.of(2000, 9, 17));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(existing));

        StudentRequest request = new StudentRequest();
        request.setFirstName("hassan");
        request.setLastName("ramadan");
        request.setEmail("hassan@gmail.com");
        request.setDateOfBirth(LocalDate.of(2000, 9, 17));

        when(studentRepository.save(any(Student.class))).thenReturn(existing);

        Student result = studentService.updateStudent(1L, request);

        assertThat(result.getFirstName()).isEqualTo("hassan");
        assertThat(result.getEmail()).isEqualTo("hassan@gmail.com");
    }

    @Test
    void studentService_deleteStudent_success() {
        Student student = new Student(1L, "ahmed", "ramadan", "ahmed@gmail.com", LocalDate.of(2000, 9, 17));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);

        verify(studentRepository).delete(student);
    }
    

}
