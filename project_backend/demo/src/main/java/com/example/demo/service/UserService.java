package com.example.demo.service;

import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    // Yeni kullanıcı oluştur
    User createUser(UserDto dto);

    // Tüm kullanıcıları DTO listesi olarak döner
    List<UserDto> getAllUsers();

    // ID ile kullanıcıyı DTO olarak döner
    UserDto getUserById(int id);

    // ID ile kullanıcıyı günceller, DTO olarak döner
    UserDto updateUser(int id, UserDto dto);

    // Kullanıcı siler
    void deleteUser(int id);

    // Sadece entity olarak kullanıcı döner (internal kullanım için)
    User getUserEntityById(Integer id);

    // AuthenticationPrincipal ile gelen kullanıcı için kullanılabilir
    User getById(Long id);
}
