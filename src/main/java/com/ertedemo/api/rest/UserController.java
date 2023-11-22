package com.ertedemo.api.rest;

import com.ertedemo.api.resource.users.CreateUserResource;
import com.ertedemo.api.resource.users.LoginCredential;
import com.ertedemo.api.resource.users.UpdateUserResource;
import com.ertedemo.api.resource.users.UserResponse;
import com.ertedemo.domain.model.entites.User;
import com.ertedemo.domain.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers() {

        List<UserResponse> responseList = userService.getAll().stream()
                .map(user ->  new UserResponse(user))
                .collect(Collectors.toList());

        return responseList;
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        Optional<User> user = userService.getById(userId);
        return ResponseEntity.ok(new UserResponse(user.get()));
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody CreateUserResource resource) {
        Optional<User> user = userService.create(new User(resource));

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user.get().getId());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping()
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserResource resource) {
        Optional<User>user = userService.getById(resource.getId());
        if(user.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        user.get().updateUser(resource);
        return ResponseEntity.ok(new UserResponse(userService.update(user.get()).get()));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        return userService.delete(userId);
    }


    //EXTRA METHODOS
    @PutMapping("/rate-user/{userId}/{rate}")
    public ResponseEntity<UserResponse> rateUser(@PathVariable Long userId, @PathVariable Float rate) {
        Optional<User> user = userService.getById(userId);
        if (rate < 0 || rate > 5 || user.isEmpty()) {
            throw new IllegalArgumentException("The rating must be in the range of 0 to 5, or user not found");
        }
        user.get().setRankPoints(rate);
        Optional<User> userUpdate = userService.update(user.get());

        return ResponseEntity.ok(new UserResponse(userUpdate.get()));
    }

    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody LoginCredential loginCredential) {

        return
                ResponseEntity.ok(
                userService.login(loginCredential.getEmail(), loginCredential.getPassword()));
    }
}
