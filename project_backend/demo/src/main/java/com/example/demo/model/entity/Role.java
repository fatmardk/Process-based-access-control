package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "SEC_ROLE")
@Data
public class Role {

    @Id
    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "role_explanation")
    private String explanation;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<SecMatrix> matrixEntries;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<UserRole> users;

    @OneToMany(mappedBy = "role")
    private List<SecMatrix> processes;


}
