package com.example.demo.repository;

import com.example.demo.entity.SecProcess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessRepository extends JpaRepository<SecProcess, String> {
}
