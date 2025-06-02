package com.codewithkhang.learnspring.service;


import com.codewithkhang.learnspring.dto.request.PermissionRequest;
import com.codewithkhang.learnspring.dto.response.PermissonResponse;
import com.codewithkhang.learnspring.entity.Permission;
import com.codewithkhang.learnspring.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;

    public Permission createPermission(PermissionRequest request) {
        Permission permission = new Permission();

        permission.setName(request.getName());
        permission.setDescription(request.getDescription());

        return permissionRepository.save(permission);
    }

    public List<PermissonResponse> findAllPermissions() {
        var permission = permissionRepository.findAll();
        return permission.stream().map(p -> {
            PermissonResponse perm = new PermissonResponse();
            perm.setName(p.getName());
            perm.setDescription(p.getDescription());
            return perm;
        }).toList();
    }

    public void deletePermission(String id) {
        permissionRepository.deleteById(id);
    }
}
