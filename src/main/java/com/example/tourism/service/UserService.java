package com.example.tourism.service;

import com.example.tourism.model.User;
import com.example.tourism.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(user -> !user.isDeleted())
                .collect(Collectors.toList());
    }

    @Transactional
    public User createUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .filter(u -> !u.isDeleted())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setRole(user.getRole());
        existingUser.setActive(user.isActive());

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Transactional
    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .filter(u -> !u.isDeleted())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        user.setDeleted(true);
        user.setActive(false);
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .filter(user -> !user.isDeleted());
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username)
                .filter(user -> !user.isDeleted())
                .isPresent();
    }

    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email)
                .filter(user -> !user.isDeleted())
                .isPresent();
    }

    public User registerNewUser(String username, String password, String email, String firstName, String lastName, boolean isAdmin) {
        if (existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        if (existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setActive(true);
        user.setRole(isAdmin ? "ROLE_ADMIN" : "ROLE_USER");

        return userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .filter(user -> !user.isDeleted())
                .orElse(null);
    }

    public boolean isAdmin(User user) {
        return user != null && !user.isDeleted() && "ROLE_ADMIN".equals(user.getRole());
    }
}