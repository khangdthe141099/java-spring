package com.codewithkhang.learnspring.controller;

import com.codewithkhang.learnspring.dto.response.ApiResponse;
import com.codewithkhang.learnspring.dto.request.UserCreationRequest;
import com.codewithkhang.learnspring.dto.request.UserUpdateRequest;
import com.codewithkhang.learnspring.entity.User;
import com.codewithkhang.learnspring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.createUser(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<User>> getAllUsers() {
        ApiResponse<List<User>> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.getAllUsers());
        return apiResponse;
    }

    @GetMapping("/{id}")
    ApiResponse<User> getUserById(@PathVariable String id) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.getUserById(id));
        return apiResponse;
    }

    @PutMapping("/{id}")
    ApiResponse<User> updateUser(@PathVariable String id, @RequestBody UserUpdateRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setData(userService.updateUser(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteUser(@PathVariable String id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        userService.deleteUser(id);
        apiResponse.setData("User deleted successfully");
        return apiResponse;
    }
}
