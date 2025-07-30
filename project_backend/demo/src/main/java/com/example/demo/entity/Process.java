package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "SEC_PROCESS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Process {

    @Id
    @Column(name = "process_code")
    private String processCode;

    @Column(name = "process_explanation")
    private String explanation;

    @OneToMany(mappedBy = "process")
    private List<SecMatrix> roleAccess;
}

