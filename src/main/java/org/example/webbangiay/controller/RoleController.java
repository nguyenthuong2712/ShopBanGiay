package org.example.webbangiay.controller;

import org.example.webbangiay.dto.request.RoleRequest;
import org.example.webbangiay.dto.response.ApiResponse;
import org.example.webbangiay.dto.response.RoleResponse;
import org.example.webbangiay.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(1000)
                .result(roleService.createRole(request))
                .build();
    }
    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles(){
        return ApiResponse.<List<RoleResponse>>builder()
                .code(1000)
                .result(roleService.getAllRoles())
                .build();
    }

    @DeleteMapping("/{role}")
    public ApiResponse<Void> deleteRole(@PathVariable String role){
        roleService.deleteRole(role);
        return ApiResponse.<Void>builder()
                .code(1000)
                .build();
    }

}
