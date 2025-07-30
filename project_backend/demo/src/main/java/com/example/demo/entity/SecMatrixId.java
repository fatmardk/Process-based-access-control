package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecMatrixId implements Serializable {

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "process_code")
    private String processCode;
}