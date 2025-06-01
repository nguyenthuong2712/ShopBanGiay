package org.example.webbangiay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.webbangiay.dto.request.RoleRequest;
import org.example.webbangiay.entity.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleResponse {
    private String name;
    private String description;
    private Set<PermissionResponse> permissions;

    public static RoleResponse fromRoleEntity(Role role) {
        return RoleResponse.builder()
                .name(role.getName())
                .description(role.getDescription())
                .permissions(
                        role.getPermission() != null
                                ? role.getPermission().stream()
                                .map(PermissionResponse::fromPermissionEntity)
                                .collect(Collectors.toSet())
                                : null
                )
                .build();
    }


    public static Role toRoleEntity(RoleRequest request) {
        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        return role;
    }
}
