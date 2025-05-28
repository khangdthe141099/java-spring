package com.codewithkhang.learnspring.controller;

import com.codewithkhang.learnspring.dto.request.AuthRequest;
import com.codewithkhang.learnspring.dto.response.ApiResponse;
import com.codewithkhang.learnspring.dto.response.AuthResponse;
import com.codewithkhang.learnspring.service.AuthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @PostMapping("/login")
    ApiResponse<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        var result = authService.authenticate(request);
        return ApiResponse.<AuthResponse>builder()
                .data(result)
                .message("Authentication successful")
                .build();
    }
}
