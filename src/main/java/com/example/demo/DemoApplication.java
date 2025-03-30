package com.example.demo;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner testConnection(DataSource dataSource) {
        return args -> {
            System.out.println("Testing database connection...");
            try (Connection connection = dataSource.getConnection()) {
                System.out.println("Database connection successful!");
                System.out.println("JDBC URL: " + connection.getMetaData().getURL());
                System.out.println("JDBC Driver: " + connection.getMetaData().getDriverName());
                System.out.println("Database product: " + connection.getMetaData().getDatabaseProductName());
            } catch (SQLException e) {
                System.err.println("Database connection failed!");
                e.printStackTrace();
            }
        };
    }
    
  
    public CommandLineRunner loadTestData(UserService userService) {
        return args -> {
            System.out.println("Creating test data...");
            try {
                // 先检查是否已有数据
                if (userService.getAllUsers().isEmpty()) {
                    System.out.println("No users found, creating test users...");
                    
                    // 创建测试用户
                    User user1 = new User();
                    user1.setUsername("testuser1");
                    user1.setEmail("test1@example.com");
                    userService.createUser(user1);
                    
                    User user2 = new User();
                    user2.setUsername("testuser2");
                    user2.setEmail("test2@example.com");
                    userService.createUser(user2);
                    
                    System.out.println("Test users created successfully!");
                } else {
                    System.out.println("Users already exist in the database.");
                }
            } catch (Exception e) {
                System.err.println("Error creating test data!");
                e.printStackTrace();
            }
        };
    }
} 