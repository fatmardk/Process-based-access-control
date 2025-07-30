package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.AuthorizationService;
import com.example.demo.service.impl.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthorizationService authorizationService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    // GET /api/user/all → Tüm kullanıcıları getir
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        if (!authorizationService.hasAccessToProcess(currentUser, "READ_USERS")) {
            return ResponseEntity.status(403).body("You do not have access to this process");
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        UserDto dto = userService.getUserById(user.getId());
        return ResponseEntity.ok(dto);
    }



    // GET /api/user/{id} → Belirli kullanıcıyı getir
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        if (!authorizationService.hasAccessToProcess(currentUser, "READ_USERS")) {
            return ResponseEntity.status(403).body("You do not have access to this process");
        }
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // PUT /api/user/update → Kendi bilgilerini güncelle
    @PutMapping("/update")
    public ResponseEntity<?> updateCurrentUser(@RequestBody UserDto dto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        if (!authorizationService.hasAccessToProcess(currentUser, "UPDATE_USER_PROFILE")) {
            return ResponseEntity.status(403).body("You do not have access to this process");
        }
        return ResponseEntity.ok(userService.updateUser(currentUser.getId(), dto));
    }

    // PUT /api/user/update/{id} → Admin başka kullanıcıyı günceller
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUserByAdmin(@PathVariable int id, @RequestBody UserDto dto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        if (!authorizationService.hasAccessToProcess(currentUser, "UPDATE_USER_PROFILE")) {
            return ResponseEntity.status(403).body("You do not have access to this process");
        }
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    // DELETE /api/user/{id} → Kullanıcı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        if (!authorizationService.hasAccessToProcess(currentUser, "DELETE_USER")) {
            return ResponseEntity.status(403).body("You do not have access to this process");
        }
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }
}
