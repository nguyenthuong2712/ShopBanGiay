package org.example.webbangiay.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webbangiay.dto.request.PermissionRequest;
import org.example.webbangiay.dto.response.PermissionResponse;
import org.example.webbangiay.entity.Permission;
import org.example.webbangiay.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionResponse create (PermissionRequest request) {
      Permission permission = PermissionResponse.toPermissionEntity(request);
      permissionRepository.save(permission);
      return PermissionResponse.fromPermissionEntity(permission);
    }

    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(PermissionResponse::fromPermissionEntity)
                .toList();
    }
    public void deletePermission (String permission) {
        permissionRepository.deleteById(permission);
    }
}
