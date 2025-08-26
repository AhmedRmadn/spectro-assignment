package com.systems.spectro_assignment.controllers;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.systems.spectro_assignment.models.Student;
import com.systems.spectro_assignment.requests.StudentRequest;
import com.systems.spectro_assignment.responses.ApiResponse;
import com.systems.spectro_assignment.services.StudentService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/user/get-all")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        List<Student> data = studentService.getAllStudents();
        ApiResponse<List<Student>> response = new ApiResponse<>();
        response.setData(data);
        response.setSuccess(true);
        response.setMessage("Fetched all students successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/get/{id}")
    public ResponseEntity<ApiResponse<Student>> getStudentById(@PathVariable Long id) {
        Student data = studentService.getStudentById(id);
        ApiResponse<Student> response = new ApiResponse<>();
        response.setData(data);
        response.setSuccess(true);
        response.setMessage("Fetched student successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<ApiResponse<Student>> createStudent(@Valid @RequestBody StudentRequest studentRequest) {
        Student data = studentService.createStudent(studentRequest);
        ApiResponse<Student> response = new ApiResponse<>();
        response.setData(data);
        response.setSuccess(true);
        response.setMessage("Student added successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<ApiResponse<Student>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentRequest studentRequest) {
        Student data = studentService.updateStudent(id, studentRequest);
        ApiResponse<Student> response = new ApiResponse<>();
        response.setData(data);
        response.setSuccess(true);
        response.setMessage("Student updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        ApiResponse<Void> response = new ApiResponse<>();
        response.setSuccess(true);
        response.setMessage("Student deleted successfully");
        return ResponseEntity.ok(response);
    }
}

