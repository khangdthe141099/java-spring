package com.codewithkhang.learnspring.controller;

import com.codewithkhang.learnspring.dto.request.PermissionRequest;
import com.codewithkhang.learnspring.dto.request.RoleRequest;
import com.codewithkhang.learnspring.dto.response.ApiResponse;
import com.codewithkhang.learnspring.dto.response.PermissonResponse;
import com.codewithkhang.learnspring.dto.response.RoleResponse;
import com.codewithkhang.learnspring.entity.Permission;
import com.codewithkhang.learnspring.entity.Role;
import com.codewithkhang.learnspring.service.RoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<Role> createRole(@RequestBody @Valid RoleRequest request) {
        ApiResponse<Role> apiResponse = new ApiResponse<>();
        apiResponse.setData(roleService.createRole(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> findAllRoles() {
        return ApiResponse.<List<RoleResponse>>builder()
                .data(roleService.findAllRoles())
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteRole(@PathVariable String id) {
        ApiResponse<String> apiResponse = new ApiResponse<>();
        roleService.deleteRole(id);
        apiResponse.setData("Role deleted successfully");
        return apiResponse;
    }
}
