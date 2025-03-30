package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;


@Repository
public class UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> findAll() {
        try {
            String sql = "SELECT * FROM users FETCH FIRST 100 ROWS ONLY";
            System.out.println("Executing SQL: " + sql);
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            System.err.println("Error in findAll(): " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public User findById(Long id) {
        try {
            String sql = "SELECT * FROM users WHERE id = :id";
            SqlParameterSource params = new MapSqlParameterSource("id", id);
            System.out.println("Executing SQL: " + sql + " with params: " + params);
            return jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            System.err.println("Error in findById(" + id + "): " + e.getMessage());
            throw e;
        }
    }

    public User save(User user) {
        if (user.getId() == null) {
            return insert(user);
        } else {
            return update(user);
        }
    }

    private User insert(User user) {
        try {
            String sql = "INSERT INTO users (username, email) VALUES (:username, :email)";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("username", user.getUsername())
                    .addValue("email", user.getEmail());
            System.out.println("Executing SQL: " + sql + " with params: " + params);
            
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(sql, params, keyHolder);
            
            if (keyHolder.getKey() == null) {
                Long id = jdbcTemplate.queryForObject(
                    "SELECT IDENTITY_VAL_LOCAL() FROM SYSIBM.SYSDUMMY1", 
                    new MapSqlParameterSource(), Long.class);
                user.setId(id);
            } else {
                user.setId(keyHolder.getKey().longValue());
            }
            return user;
        } catch (Exception e) {
            System.err.println("Error in insert(): " + e.getMessage());
            throw new RuntimeException("Error inserting user: " + e.getMessage(), e);
        }
    }

    private User update(User user) {
        try {
            String sql = "UPDATE users SET username = :username, email = :email WHERE id = :id";
            SqlParameterSource params = new MapSqlParameterSource()
                    .addValue("id", user.getId())
                    .addValue("username", user.getUsername())
                    .addValue("email", user.getEmail());
            System.out.println("Executing SQL: " + sql + " with params: " + params);
            
            int affected = jdbcTemplate.update(sql, params);
            if (affected == 0) {
                throw new RuntimeException("No rows updated for user id: " + user.getId());
            }
            return user;
        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    public void deleteById(Long id) {
        try {
            String sql = "DELETE FROM users WHERE id = :id";
            SqlParameterSource params = new MapSqlParameterSource("id", id);
            System.out.println("Executing SQL: " + sql + " with params: " + params);
            
            int affected = jdbcTemplate.update(sql, params);
            if (affected == 0) {
                throw new RuntimeException("No rows deleted for user id: " + id);
            }
        } catch (Exception e) {
            System.err.println("Error in deleteById(): " + e.getMessage());
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }
} 