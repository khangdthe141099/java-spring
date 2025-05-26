package com.codewithkhang.learnspring.service;

import com.codewithkhang.learnspring.dto.request.UserCreationRequest;
import com.codewithkhang.learnspring.dto.request.UserUpdateRequest;
import com.codewithkhang.learnspring.entity.User;
import com.codewithkhang.learnspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request) {
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob() != null ? request.getDob() : LocalDate.now());

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User updateUser(String id, UserUpdateRequest request) {
        User user = getUserById(id);

        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob() != null ? request.getDob() : LocalDate.now());

        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
