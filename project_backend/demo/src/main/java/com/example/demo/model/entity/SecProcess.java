package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "SEC_PROCESS")
@Data
public class SecProcess {

    @Id
    @Column(name = "process_code")
    private String processCode;

    @Column(name = "process_explanation")
    private String processExplanation;

    @OneToMany(mappedBy = "process", fetch = FetchType.LAZY)
    private List<SecMatrix> roleAccess;
}