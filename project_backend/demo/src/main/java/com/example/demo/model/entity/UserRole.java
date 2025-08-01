package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_ROLES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_isn")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleCode")
    @JoinColumn(name = "role_code")
    private Role role;

    @Column(name = "grantable")
    private Boolean grantable;

    @Column(name = "project_id")
    private Integer projectId;
}
