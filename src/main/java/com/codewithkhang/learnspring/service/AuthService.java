package com.codewithkhang.learnspring.service;

import com.codewithkhang.learnspring.dto.request.AuthRequest;
import com.codewithkhang.learnspring.dto.response.AuthResponse;
import com.codewithkhang.learnspring.exception.AppException;
import com.codewithkhang.learnspring.exception.ErrorCode;
import com.codewithkhang.learnspring.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    UserRepository userRepository;

    public AuthResponse authenticate(AuthRequest authRequest) {
        var user = userRepository
                .findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }

        return AuthResponse.builder().authenticated(true).build();
    }
}
