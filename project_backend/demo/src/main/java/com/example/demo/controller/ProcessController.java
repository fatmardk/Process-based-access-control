package com.example.demo.controller;

import com.example.demo.dto.ProcessDto;
import com.example.demo.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/process")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessService processService;

    @GetMapping("/{code}")
    public ResponseEntity<ProcessDto> getProcessByCode(@PathVariable String code) {
        ProcessDto dto = processService.getByCode(code);
        return ResponseEntity.ok(dto);
    }
}
