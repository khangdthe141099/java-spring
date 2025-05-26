package com.codewithkhang.learnspring.controller;

import com.codewithkhang.learnspring.dto.request.UserCreationRequest;
import com.codewithkhang.learnspring.dto.request.UserUpdateRequest;
import com.codewithkhang.learnspring.entity.User;
import com.codewithkhang.learnspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    User createUser(@RequestBody UserCreationRequest request) {
        return userService.createUser(request);
    }

    @GetMapping
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    User updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
