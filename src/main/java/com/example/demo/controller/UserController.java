package com.example.demo.controller;

import com.example.demo.constant.ApiConstants;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ApiConstants.USERS_BASE_PATH)
@Tag(name = "User Management", description = "User management APIs")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Operation(summary = "Create a new user", description = "Creates a new user with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(
        path = ApiConstants.UserApi.CREATE,
        consumes = ApiConstants.UserApi.CONSUMES,
        produces = ApiConstants.UserApi.PRODUCES
    )
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(
        path = ApiConstants.UserApi.GET_ALL,
        produces = ApiConstants.UserApi.PRODUCES
    )
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("GET /api/users/all - Retrieving all users");
        List<User> users = userService.getAllUsers();
        System.out.println("GET /api/users/all - Retrieved " + users.size() + " users");
        return ResponseEntity.ok(users);
    }
    
    @Operation(summary = "Test database connection", description = "Simple test to check database connection")
    @PostMapping(
        path = ApiConstants.UserApi.TEST,
        produces = ApiConstants.UserApi.PRODUCES
    )
    public ResponseEntity<Map<String, Object>> testDatabase() {
        Map<String, Object> response = new HashMap<>();
        try {
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
            
            String dbVersion = jdbcTemplate.queryForObject(
                "SELECT GETVARIABLE('SYSIBM.VERSION') FROM SYSIBM.SYSDUMMY1", String.class);
            
            response.put("success", true);
            response.put("count", count);
            response.put("dbVersion", dbVersion);
            response.put("message", "DB2 connection successful");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            response.put("message", "Database connection failed");
            return ResponseEntity.status(500).body(response);
        }
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(
        path = ApiConstants.UserApi.FIND_BY_ID,
        consumes = ApiConstants.UserApi.CONSUMES,
        produces = ApiConstants.UserApi.PRODUCES
    )
    public ResponseEntity<User> getUserById(@RequestBody Map<String, String> payload) {
        BigDecimal id = new BigDecimal(payload.get("id"));
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Update a user", description = "Updates a user with the provided information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(
        path = ApiConstants.UserApi.UPDATE,
        consumes = ApiConstants.UserApi.CONSUMES,
        produces = ApiConstants.UserApi.PRODUCES
    )
    public ResponseEntity<User> updateUser(@RequestBody Map<String, Object> payload) {
        BigDecimal id = new BigDecimal(payload.get("id").toString());
        
        User user = new User();
        user.setUsername((String) payload.get("username"));
        user.setEmail((String) payload.get("email"));
        
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping(
        path = ApiConstants.UserApi.DELETE,
        consumes = ApiConstants.UserApi.CONSUMES,
        produces = ApiConstants.UserApi.PRODUCES
    )
    public ResponseEntity<Void> deleteUser(@RequestBody Map<String, String> payload) {
        BigDecimal id = new BigDecimal(payload.get("id"));
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
} 