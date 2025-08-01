package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SEC_MATRIX")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecMatrix {

    @EmbeddedId
    private SecMatrixId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleCode")
    @JoinColumn(name = "role_code")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("processCode")
    @JoinColumn(name = "process_code")
    private SecProcess process;

    public String getRoleCode() {
        return id != null ? id.getRoleCode() : null;
    }

    public String getProcessCode() {
        return id != null ? id.getProcessCode() : null;
    }
}
