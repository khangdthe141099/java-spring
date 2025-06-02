package com.codewithkhang.learnspring.service;

import com.codewithkhang.learnspring.dto.request.RoleRequest;
import com.codewithkhang.learnspring.dto.response.RoleResponse;
import com.codewithkhang.learnspring.entity.Role;
import com.codewithkhang.learnspring.repository.PermissionRepository;
import com.codewithkhang.learnspring.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    public Role createRole(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        return roleRepository.save(role);
    }

    public List<RoleResponse> findAllRoles() {
        return roleRepository.findAll().stream().map(role -> {
            RoleResponse response = new RoleResponse();
            response.setName(role.getName());
            response.setDescription(role.getDescription());
            response.setPermissions(role.getPermissions());
            return response;
        }).toList();
    }

    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }
}
