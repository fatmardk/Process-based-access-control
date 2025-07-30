package com.example.demo.mapper;

import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;

import java.util.stream.Collectors;

public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setIsActive(user.getIsActive());

        if (user.getRoles() != null) {
            dto.setRoles(user.getRoles().stream().map(userRole -> {
                RoleDto roleDto = new RoleDto();
                roleDto.setRoleCode(userRole.getRole().getRoleCode());
                roleDto.setGrantable(userRole.getGrantable());
                roleDto.setProjectId(userRole.getProjectId());
                return roleDto;
            }).collect(Collectors.toList()));
        }

        return dto;
    }

    public static User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getUserId() != null ? dto.getUserId().intValue() : null);
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFullName(dto.getFullName());
        user.setIsActive(Boolean.TRUE); // default aktif
        return user;
    }
}
