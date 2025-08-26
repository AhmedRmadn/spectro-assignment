# Secure Student Management API 🔒🎓


A Spring Boot RESTful API for managing student records with JWT-based authentication and role-based access control.
This project was built as part of the Spectro Systems Assignment.

[![Java](https://img.shields.io/badge/Java-17-blue.svg)](https://www.java.com)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](https://spring.io/projects/spring-boot)

***

## ✨ Key Features

* **CRUD Operations on students**:
   - Create, Retrieve, Update, Delete.
* **Spring Security with JWT**:
   - Authentication via /api/auth/login and /api/auth/register
   - Stateless token-based authentication.
* **Role-based Access Control**:
   - ADMIN → Full CRUD access.
   - USER → Read-only access
* **Input Validation**:
   - Enforced via Jakarta Validation (@NotBlank, @Email, @MinAge, etc.).
* **Global Exception Handling**:
   - Returns meaningful error responses with proper HTTP codes.
* **Persistence Layer**:
   - Spring Data JPA with H2 in-memory database.
* **Unit & Integration Tests**:
   - Repository and Service layer tested with JUnit5, Mockito, and H2.

***

## ⚙️ Tech Stack

* **Java 17**.
* **Spring Boot 3**.
* **Spring Security + JWT**.
* **Spring Data JPA + H2 Database**.
* **Maven**.
* **JUnit 5 + Mockito**.

***

## 🚀 Getting Started

Follow these instructions to get a local copy up and running for development and testing.

### Installation & Setup

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/AhmedRmadn/spectro-assignment.git
    cd spectro-assignment
    ```
2.  **Build and run the application:**
    ```sh
    # Build the project and run tests
    mvn clean install
    
    # Build the project and skip run tests
    mvn clean install -DskipTests
    
    # Run the application
    mvn spring-boot:run
    Or
    java -jar target/souqly-0.0.1-SNAPSHOT.jar
    ```
    The API will be available at `http://localhost:8080`.

***


## 📖 API Documentation

All endpoints require a JWT Bearer Token in the `Authorization` header.

<details>
<summary>🔐 <strong>UserController</strong></summary>

### 🚪 Sign In
- **Method**: `POST`
- **Endpoint**: `/api/auth/login`
- **Description**: Authenticates a user and returns a JWT token.
- **Request Body**:
  ```json
  {
    "username": "admin1",
    "password": "password123"
  }
  ```
- **Response** (`200 OK`):
  ```json
  {
    "success": true,
    "data": {
        "token": "jwt-token",
        "tokenType": "Bearer"
    },
    "message": "Login successful"
  }
  ```

---

### 🆕 Sign Up
- **Method**: `POST`
- **Endpoint**: `/api/auth/register`
- **Description**: Registers a new user and return jwt token.
- **Request Body**:
  ```json
  {
  "username": "ahmed",
  "password": "password123",
  "roles": ["ADMIN","USER"]
  }
  ```
- **Response** (`201 CREATED`):
  ```json
  {
    "success": true,
    "data": {
        "token": "jwt-token" ,
        "tokenType": "Bearer"
    },
    "message": "User registered successfully"
  }
  ```
</details>

---

<details>
<summary>🏠 <strong>StudentController</strong></summary>

### ➕ Add New Student
- **Method**: `POST`
- **Endpoint**: `/api/student/admin/add`
- **Authorization🔐**: secured and only Admins could access.
- **Request Body**:
  ```json
  {
  "firstName": "Ahmed",
  "lastName": "Ramadan",
  "email": "ahmed.ramadan@example.com",
  "dateOfBirth": "2010-06-15"
  }

  ```
- **Response** (`201 CREATED`):
  ```json
  {
    "success": true,
    "data": {
        "id": 1,
        "firstName": "Ahmed",
        "lastName": "Ramadan",
        "email": "ahmed.ramadan@example.com",
        "dateOfBirth": "2010-06-15"
    },
    "message": "Student added successfully"
  }
  ```

---

### ✏️ Update Student Data
- **Method**: `PUT`
- **Endpoint**: `/api/student/admin/update/{student_id}`
- **Authorization🔐**: secured and only Admins could access.
- **Request Body**:
  ```json
  {
  "firstName": "Hassan",
  "lastName": "Ramadan",
  "email": "ahmed.ramadan@example.com",
  "dateOfBirth": "2010-06-15"
  }

  ```
- **Response** (`200 OK`):
  ```json
  {
    "success": true,
    "data": {
        "id": 2,
        "firstName": "Hassan",
        "lastName": "Ramadan",
        "email": "ahmed.ramadan@example.com",
        "dateOfBirth": "2010-06-15"
    },
    "message": "Student updated successfully"
  }
  ```

---

### ❌ Delete Student
- **Method**: `DELETE`
- **Endpoint**: `/api/student/admin/delete/{student_id}`
- **Authorization🔐**: secured and only Admins could access.
- **Response** (`200 OK`):
  ```json
  {
    "success": true,
    "message": "Student deleted successfully",
    "data": null
  }
  ```

---

### 📋 Get All Student data
- **Method**: `GET`
- **Endpoint**: `/api/student/user/get-all`
- **Authorization🔐**: secured and user with USER or ADMIN role could fetch it.
- **Response** (`200 OK`):
  ```json
  {
    "success": true,
    "data": [
        {
            "id": 2,
            "firstName": "Hassan",
            "lastName": "Ramadan",
            "email": "ahmed.ramadan@example.com",
            "dateOfBirth": "2010-06-15"
        }
    ],
    "message": "Fetched all students successfully"
  }
  ```

---

### 🆔 Get Student by ID
- **Method**: `GET`
- **Endpoint**: `/api/student/user/get/{student_id}`
- **Authorization🔐**: secured and user with USER or ADMIN role could fetch it.
- **Response** (`200 OK`):
  ```json
  {
    "success": true,
    "data": {
        "id": 2,
        "firstName": "Hassan",
        "lastName": "Ramadan",
        "email": "ahmed.ramadan@example.com",
        "dateOfBirth": "2010-06-15"
    },
    "message": "Fetched student successfully"
  }
  ```

</details>

---

## Testing

This project includes **unit tests** and **repository tests** to ensure the functionality of services and database interactions.  

### 1. Unit Tests (Service Layer)
- **StudentServiceTest**  
  - Tests student creation, update, deletion, and retrieval.  
  - Checks for exceptions (e.g., duplicate email, student not found).  

- **UserServiceTest**  
  - Tests user creation, login, and authentication flows.  
  - Handles duplicate usernames and login failures.

**Frameworks used:**  
- JUnit 5 (`junit-jupiter`)  
- Mockito (`mockito-core`)  
- AssertJ (`assertj-core`)  

### 2. Repository Tests
- **StudentRepositoryTest**  
  - Tests CRUD operations on `Student` entity.  
  - Uses **H2 in-memory database** for isolation.  
  - Tests `existsByEmail`, save, update, delete, and findById methods.

**Annotations used:**  
- `@DataJpaTest` → sets up a lightweight Spring Data JPA environment.  
- `@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)` → ensures H2 is used.  
- `@ActiveProfiles("test")` → separates test configuration from main app.


## 🤝 Contributing

Contributions are welcome! Please fork the repo and submit a pull request.
