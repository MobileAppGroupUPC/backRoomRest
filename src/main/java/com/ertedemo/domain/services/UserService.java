package com.ertedemo.domain.services;

import com.ertedemo.api.resource.users.UpdateUserResource;
import com.ertedemo.domain.model.entites.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    Optional<User> getById(Long userId);

    Optional<User> create(User user);

    public Optional<User> update(User user);

    ResponseEntity<?> delete(Long userId);


    public Long login(String email, String password);
}