package com.example.demo.repository;

import com.example.demo.model.entity.Process;
import com.example.demo.model.entity.SecProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProcessRepository extends JpaRepository<SecProcess, String> {
    Optional<Process> findByProcessCode(String code);

}
