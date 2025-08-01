package com.example.demo.controller;

import com.example.demo.exception.AccessDeniedException;
import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.service.SecMatrixService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecMatrixService secMatrixService;

    private static final String READ_USERS = "READ_USERS";
    private static final String UPDATE_USER_PROFILE = "UPDATE_USER_PROFILE";
    private static final String DELETE_USER = "DELETE_USER";

    private boolean hasProcessAccess(User user, String processCode) {
        return secMatrixService.hasAccessToProcess(user, processCode);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        if (!hasProcessAccess(currentUser, READ_USERS)) {
            throw new AccessDeniedException(READ_USERS);
        }

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        UserDto userDto = userService.getUserById(currentUser.getId());
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        if (!hasProcessAccess(currentUser, READ_USERS)) {
            throw new AccessDeniedException(READ_USERS);
        }

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCurrentUser(@RequestBody UserDto dto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        if (!hasProcessAccess(currentUser, UPDATE_USER_PROFILE)) {
            throw new AccessDeniedException(UPDATE_USER_PROFILE);
        }

        return ResponseEntity.ok(userService.updateUser(currentUser.getId(), dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserByAdmin(@PathVariable int id, @RequestBody UserDto dto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        if (!hasProcessAccess(currentUser, UPDATE_USER_PROFILE)) {
            throw new AccessDeniedException(UPDATE_USER_PROFILE);
        }

        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        if (!hasProcessAccess(currentUser, DELETE_USER)) {
            throw new AccessDeniedException(DELETE_USER);
        }

        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
