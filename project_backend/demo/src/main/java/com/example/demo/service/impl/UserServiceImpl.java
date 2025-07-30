package com.example.demo.service.impl;

import com.example.demo.dto.ProcessDto;
import com.example.demo.dto.RoleDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.UserRole;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ActivityLogService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ActivityLogService activityLogService;

    @Override
    @CachePut(value = "users", key = "#result.id")
    public User createUser(UserDto dto) {
        User user = UserMapper.toEntity(dto);
        return userRepository.save(user);
    }

    @Override
    @Cacheable(value = "allUsers")
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "users", key = "#id")
    public UserDto getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public User getUserEntityById(Integer id) {
        return userRepository.findWithRolesById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id.intValue())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
    }



    @Override
    @CachePut(value = "users", key = "#id")
    public UserDto updateUser(int id, UserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String currentUserName = user.getUsername();

        if (dto.getUsername() != null && !dto.getUsername().equals(user.getUsername())) {
            activityLogService.logChange(currentUserName, "USERFILE", "user_name", user.getUsername(), dto.getUsername(), "UPDATE");
            user.setUsername(dto.getUsername());
        }

        if (dto.getFullName() != null && !dto.getFullName().equals(user.getFullName())) {
            activityLogService.logChange(currentUserName, "USERFILE", "full_name", user.getFullName(), dto.getFullName(), "UPDATE");
            user.setFullName(dto.getFullName());
        }

        if (dto.getEmail() != null && !dto.getEmail().equals(user.getEmail())) {
            activityLogService.logChange(currentUserName, "USERFILE", "mail_address", user.getEmail(), dto.getEmail(), "UPDATE");
            user.setEmail(dto.getEmail());
        }

        if (dto.getIsActive() != null && !dto.getIsActive().equals(user.getIsActive())) {
            activityLogService.logChange(currentUserName, "USERFILE", "is_active", String.valueOf(user.getIsActive()), String.valueOf(dto.getIsActive()), "UPDATE");
            user.setIsActive(dto.getIsActive());
        }

        if (dto.getRoles() != null && !dto.getRoles().isEmpty()) {
            user.getRoles().clear();
            for (RoleDto roleDto : dto.getRoles()) {
                Role role = new Role();
                role.setRoleCode(roleDto.getRoleCode());

                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(role);
                userRole.setGrantable(Boolean.TRUE.equals(roleDto.getGrantable()));
                userRole.setProjectId(roleDto.getProjectId() != null ? roleDto.getProjectId() : 1);

                user.getRoles().add(userRole);
            }
        }

        User updated = userRepository.save(user);
        return UserMapper.toDto(updated);
    }

    @Override
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
