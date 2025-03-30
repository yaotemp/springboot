package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Schema(description = "User entity representing a user in the system")
public class User {
    @Schema(description = "Unique identifier of the user", example = "1")
    private BigDecimal id;
    
    @Schema(description = "Username for login", example = "john_doe", required = true)
    private String username;
    
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;
    // Add other fields as needed based on your users table structure
} 