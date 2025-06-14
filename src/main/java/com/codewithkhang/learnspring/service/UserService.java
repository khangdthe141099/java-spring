package com.codewithkhang.learnspring.service;

import com.codewithkhang.learnspring.dto.request.UserCreationRequest;
import com.codewithkhang.learnspring.dto.request.UserUpdateRequest;
import com.codewithkhang.learnspring.entity.User;
import com.codewithkhang.learnspring.enums.Roles;
import com.codewithkhang.learnspring.exception.AppException;
import com.codewithkhang.learnspring.exception.ErrorCode;
import com.codewithkhang.learnspring.mapper.UserMapper;
import com.codewithkhang.learnspring.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public User createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }
        //Apply mapstruct
        //User user = userMapper.toUser(request);
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob() != null ? request.getDob() : LocalDate.now());

        HashSet<String> roles = new HashSet<>();
        roles.add(Roles.USER.name());
//        user.setRoles(roles);

        return userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
    }

    public User updateUser(String id, UserUpdateRequest request) {
        User user = getUserById(id);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob() != null ? request.getDob() : LocalDate.now());

        if (request.getRoles() != null) {
            HashSet<String> roles = new HashSet<>(request.getRoles());
//            user.setRoles(roles);
        }

        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
