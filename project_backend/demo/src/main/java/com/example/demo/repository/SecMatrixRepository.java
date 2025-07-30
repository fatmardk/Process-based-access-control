package com.example.demo.repository;

import com.example.demo.entity.SecMatrix;
import com.example.demo.entity.SecMatrixId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecMatrixRepository extends JpaRepository<SecMatrix, SecMatrixId> {

    // Role'lerin belirli bir process'e erişimi olup olmadığını kontrol et
    boolean existsByIdRoleCodeInAndIdProcessCode(List<String> roleCodes, String processCode);

    // Kullanıcının tüm role'leriyle erişebildiği işlemleri getir
    List<SecMatrix> findAllByIdRoleCodeIn(List<String> roleCodes);

    // Belirli bir role ait tüm processCode'ları getir
    List<SecMatrix> findAllByIdRoleCode(String roleCode);
}
