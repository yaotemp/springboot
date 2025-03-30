package com.example.demo.service;

import com.example.demo.model.User;
import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(BigDecimal id);
    User createUser(User user);
    User updateUser(User user);
    void deleteUser(BigDecimal id);
    
    // Add method to find users by EFIN
    User findByEfin(String efin);
} 