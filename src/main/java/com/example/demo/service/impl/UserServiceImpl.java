package com.example.demo.service.impl;

import com.example.demo.dao.UserRepository;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        try {
            System.out.println("Retrieving all users...");
            List<User> users = userRepository.findAll();
            System.out.println("Retrieved " + users.size() + " users");
            return users;
        } catch (DataAccessException e) {
            System.err.println("Error retrieving all users: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public User getUserById(BigDecimal id) {
        try {
            System.out.println("Finding user with id: " + id);
            return userRepository.findById(id);
        } catch (EmptyResultDataAccessException e) {
            System.err.println("User not found with id: " + id);
            throw new RuntimeException("User not found with id: " + id);
        } catch (DataAccessException e) {
            System.err.println("Error finding user with id " + id + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error finding user: " + e.getMessage());
        }
    }

    @Override
    public User createUser(User user) {
        try {
            System.out.println("Creating new user: " + user.getUsername());
            User savedUser = userRepository.save(user);
            System.out.println("User created with id: " + savedUser.getId());
            return savedUser;
        } catch (DataAccessException e) {
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public User updateUser(User userDetails) {
        try {
            System.out.println("Updating user with id: " + userDetails.getId());
            User user = getUserById(userDetails.getId());
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setEfin(userDetails.getEfin());
            User updatedUser = userRepository.save(user);
            System.out.println("User updated: " + updatedUser.getUsername());
            return updatedUser;
        } catch (DataAccessException e) {
            System.err.println("Error updating user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }

    @Override
    public void deleteUser(BigDecimal id) {
        try {
            System.out.println("Deleting user with id: " + id);
            getUserById(id); // Check if user exists
            userRepository.deleteById(id);
            System.out.println("User deleted with id: " + id);
        } catch (DataAccessException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public User findByEfin(String efin) {
        try {
            System.out.println("Finding user with EFIN: " + efin);
            // This is a simplified implementation - in real code you'd implement a repository method for this
            // For now, find all users and filter the one with matching EFIN
            List<User> users = getAllUsers();
            return users.stream()
                .filter(user -> efin.equals(user.getEfin()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found with EFIN: " + efin));
        } catch (DataAccessException e) {
            System.err.println("Error finding user with EFIN " + efin + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error finding user by EFIN: " + e.getMessage());
        }
    }
} 