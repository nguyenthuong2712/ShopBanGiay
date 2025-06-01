package org.example.webbangiay.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.webbangiay.dto.request.PermissionRequest;
import org.example.webbangiay.entity.Permission;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionResponse {
    private String name;
    private String description;

    public static PermissionResponse fromPermissionEntity(Permission permission) {
        PermissionResponse pm = new PermissionResponse();
        pm.setName(permission.getName());
        pm.setDescription(permission.getDescription());
        return pm;
    }

    public static Permission toPermissionEntity(PermissionRequest request) {
        Permission p = new Permission();
        p.setName(request.getName());
        p.setDescription(request.getDescription());
        return p;
    }
}
