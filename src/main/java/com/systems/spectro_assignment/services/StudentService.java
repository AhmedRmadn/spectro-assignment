package com.systems.spectro_assignment.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.systems.spectro_assignment.exceptions.StudentException;
import com.systems.spectro_assignment.models.Student;
import com.systems.spectro_assignment.repositories.StudentRepository;
import com.systems.spectro_assignment.requests.StudentRequest;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(StudentRequest studentRequest) {
        if (studentRepository.existsByEmail(studentRequest.getEmail())) {
            throw new StudentException("Email already exists: " + studentRequest.getEmail(), HttpStatus.CONFLICT);
        }
        Student student = getStudentFromRequest(studentRequest);
        return studentRepository.save(student);
    }


    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentException("Student not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    public Student updateStudent(Long id, StudentRequest studentRequest) {
    	Student student = getStudentById(id);
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setEmail(studentRequest.getEmail());
        student.setDateOfBirth(studentRequest.getDateOfBirth());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
    
    private Student getStudentFromRequest(StudentRequest studentRequest) {
    	Student student = new Student();
    	student.setFirstName(studentRequest.getFirstName());
    	student.setLastName(studentRequest.getLastName());
    	student.setEmail(studentRequest.getEmail());
    	student.setDateOfBirth(studentRequest.getDateOfBirth());
    	return student;
    }
}
