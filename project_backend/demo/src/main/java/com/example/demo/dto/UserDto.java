package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer userId;
    private String fullName;
    private String username;
    private String email;
    private Boolean isActive;
    private List<RoleDto> roles;
    private List<ProcessDto> allowedProcesses;

}

