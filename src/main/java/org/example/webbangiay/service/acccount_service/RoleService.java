package org.example.webbangiay.service.acccount_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.webbangiay.dto.request.RoleRequest;
import org.example.webbangiay.dto.response.RoleResponse;
import org.example.webbangiay.repository.PermissionRepository;
import org.example.webbangiay.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleResponse createRole(RoleRequest request){
        var role = RoleResponse.toRoleEntity(request);

        var permission = permissionRepository.findAllById(request.getPermissions());
        role.setPermission(new HashSet<>(permission));
        roleRepository.save(role);
        return RoleResponse.fromRoleEntity(role);
    }

    public List<RoleResponse> getAllRoles(){
        var roles = roleRepository.findAll();
        return roles.stream()
                .map(RoleResponse::fromRoleEntity)
                .toList();
    }

    public void deleteRole(String role){
        roleRepository.deleteById(role);
    }

}
