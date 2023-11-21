package com.ertedemo.shared.services.impl;

import com.ertedemo.api.resource.users.UpdateUserResource;
import com.ertedemo.domain.model.entites.User;
import com.ertedemo.domain.persistence.UserRepository;
import com.ertedemo.domain.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long userId) {
        return Optional.ofNullable(userRepository.findById(userId))
                .orElseThrow();
    }

    @Override
    public Optional<User> create(User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> update(User user) {
        return Optional.of(userRepository.save(user));
    }

    @Override
    public ResponseEntity<?> delete(Long userId) {
        return userRepository.findById(userId).map(student -> {
                    userRepository.delete(student);
                    return ResponseEntity.ok().build();})
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public Long login(String email, String password) {
        Optional<User> user = userRepository.findByEmailAndPassword(email, password);

        if(user.isEmpty()) return 0L;
        return user.get().getId();
    }
}
