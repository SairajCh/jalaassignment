package com.example.jalatask.controller;

import com.example.jalatask.model.User;
import com.example.jalatask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.OK);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User user) {
        User updatedUser = userService.updateUser(userId, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUserByEmail(@RequestBody Map<String, String> requestBody) {
        if (requestBody == null || requestBody.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        if (requestBody.containsKey("email")) {
            String email = requestBody.get("email");
            if (email == null || email.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            userService.deleteUserByEmail(email);
            return ResponseEntity.ok().build();
        }

        if (requestBody.containsKey("mobile")) {
            String mobile = requestBody.get("mobile");
            if (mobile == null || mobile.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            userService.deleteUserByMobile(mobile);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<User> searchUser(@PathVariable Integer userId) {
        User user = userService.searchUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}


