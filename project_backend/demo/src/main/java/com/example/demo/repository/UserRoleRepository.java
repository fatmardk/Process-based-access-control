package com.example.demo.repository;

import com.example.demo.model.entity.UserRole;
import com.example.demo.model.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUser_Id(Integer userId);
}
