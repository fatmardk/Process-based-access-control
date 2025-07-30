package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.roles ur " +
            "LEFT JOIN FETCH ur.role " +
            "WHERE u.username = :username")
    Optional<User> findByUserIsn(Integer userIsn);

    @EntityGraph(attributePaths = "roles")
    Optional<User> findWithRolesById(Integer id);

    Optional<User> findByUsername(String username);

}
