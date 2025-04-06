# Spring Boot Mainframe DB2 Integration Application

This project demonstrates how to connect and interact with a DB2 database on a Mainframe system using Spring Boot and NamedParameterJdbcTemplate.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Configuration](#configuration)
- [API Documentation](#api-documentation)
- [Development Guide](#development-guide)
- [Deployment](#deployment)
- [API Development Example](#api-development-example)

## Prerequisites

- Java 17 or later
- Maven 3.8 or later
- Access to a Mainframe DB2 database
- IBM DB2 JDBC driver (included in the project)

## Project Structure

```
src/main/java/com/example/demo/
├── config/         # Configuration classes
├── constant/       # Application constants
├── controller/     # REST API controllers
├── dao/           # Data Access Objects
├── model/         # Data models/entities
├── service/       # Business logic services
└── DemoApplication.java
```

## Technology Stack

- Spring Boot 3.2.3
- Spring JDBC (NamedParameterJdbcTemplate)
- IBM DB2 JDBC Driver 11.5.8.0
- Springdoc OpenAPI 2.3.0 (Swagger)
- Lombok
- Maven

## Configuration

### Database Connection

Configure your database connection in `application.properties`:

```properties
spring.datasource.jdbcUrl=jdbc:db2://mainframe-host:446/DBNAME
spring.datasource.username=db2user
spring.datasource.password=db2pass
spring.datasource.driver-class-name=com.ibm.db2.jcc.DB2Driver
spring.datasource.hikari.data-source-properties.currentSchema=SCHEMA
```

Replace the following placeholders:
- `mainframe-host`: Your Mainframe hostname
- `DBNAME`: Your DB2 database name
- `db2user`: DB2 username
- `db2pass`: DB2 password
- `SCHEMA`: DB2 schema name

## API Documentation

The application provides the following REST API endpoints:

1. **Create User**: `POST /api/users`
2. **Get All Users**: `POST /api/users/all`
3. **Find User**: `POST /api/users/find`
4. **Update User**: `POST /api/users/update`
5. **Delete User**: `POST /api/users/delete`
6. **Test Connection**: `POST /api/users/test`

Access the Swagger UI at: `http://localhost:8080/swagger-ui.html`

## Development Guide

### Setting Up Development Environment

1. Clone the repository
2. Import the project into your IDE as a Maven project
3. Configure your database connection in `application.properties`
4. Install dependencies: `mvn clean install`

### Adding New Features

1. Create new model classes in `model` package
2. Add DAO interfaces in `dao` package
3. Implement services in `service` package
4. Create controllers in `controller` package
5. Add appropriate tests

### Code Style

- Follow Java naming conventions
- Use Lombok annotations for boilerplate code
- Document public methods and classes
- Follow REST API best practices

## Deployment

1. Build the application:
```bash
mvn clean package
```

2. Run the application:
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## API Development Example

This section provides a simple example of how to create a GET query API endpoint.

### Create Model Class

Create a model class in `src/main/java/com/example/demo/model/Employee.java`:

```java
package com.example.demo.model;

import lombok.Data;

@Data
public class Employee {
    private String empId;
    private String name;
    private String department;
    private String position;
}
```

### Create DAO Interface

Create a DAO interface in `src/main/java/com/example/demo/dao/EmployeeDao.java`:

```java
package com.example.demo.dao;

import com.example.demo.model.Employee;
import java.util.List;
import java.util.Map;

public interface EmployeeDao {
    List<Employee> findByDepartment(String department);
}
```

### Implement DAO

Create implementation in `src/main/java/com/example/demo/dao/EmployeeDaoImpl.java`:

```java
package com.example.demo.dao;

import com.example.demo.model.Employee;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public EmployeeDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Employee> findByDepartment(String department) {
        String sql = "SELECT emp_id, name, department, position " +
                    "FROM employees " +
                    "WHERE department = :department";
        
        Map<String, Object> params = new HashMap<>();
        params.put("department", department);
        
        return jdbcTemplate.query(sql, params, (rs, rowNum) -> {
            Employee employee = new Employee();
            employee.setEmpId(rs.getString("emp_id"));
            employee.setName(rs.getString("name"));
            employee.setDepartment(rs.getString("department"));
            employee.setPosition(rs.getString("position"));
            return employee;
        });
    }
}
```

### Create Service

Create a service class in `src/main/java/com/example/demo/service/EmployeeService.java`:

```java
package com.example.demo.service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.model.Employee;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeDao employeeDao;

    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeDao.findByDepartment(department);
    }
}
```

### Create Controller

Create a controller class in `src/main/java/com/example/demo/controller/EmployeeController.java`:

```java
package com.example.demo.controller;

import com.example.demo.model.Employee;
import com.example.demo.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(
            @PathVariable String department) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(department));
    }
}
```

### Test the API

You can test the API using the following curl command:

```bash
# Get employees by department
curl -X GET "http://localhost:8080/api/employees/department/IT"
```

The response will be in JSON format:

```json
[
    {
        "empId": "E001",
        "name": "John Doe",
        "department": "IT",
        "position": "Developer"
    },
    {
        "empId": "E002",
        "name": "Jane Smith",
        "department": "IT",
        "position": "Analyst"
    }
]
```

### API Documentation

The API endpoint will be automatically documented in Swagger UI at:
`http://localhost:8080/swagger-ui.html` 