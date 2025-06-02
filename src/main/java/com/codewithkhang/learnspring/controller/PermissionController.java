package com.codewithkhang.learnspring.controller;

import com.codewithkhang.learnspring.dto.request.PermissionRequest;
import com.codewithkhang.learnspring.dto.request.UserCreationRequest;
import com.codewithkhang.learnspring.dto.response.ApiResponse;
import com.codewithkhang.learnspring.dto.response.PermissonResponse;
import com.codewithkhang.learnspring.entity.Permission;
import com.codewithkhang.learnspring.entity.User;
import com.codewithkhang.learnspring.service.PermissionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<Permission> createPermission(@RequestBody @Valid PermissionRequest request) {
        ApiResponse<Permission> apiResponse = new ApiResponse<>();
        apiResponse.setData(permissionService.createPermission(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<PermissonResponse>> getAllPermission() {
        return ApiResponse.<List<PermissonResponse>>builder()
                .data(permissionService.findAllPermissions())
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deletePermission(@PathVariable String id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        permissionService.deletePermission(id);
        apiResponse.setData("Permission deleted successfully");
        return apiResponse;
    }
}
