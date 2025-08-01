package com.example.demo.service.impl;

import com.example.demo.model.dto.ProcessDto;
import com.example.demo.model.entity.Process;
import com.example.demo.repository.ProcessRepository;
import com.example.demo.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final ProcessRepository processRepository;

    @Override
    public ProcessDto getByCode(String code) {
        Process process = processRepository.findByProcessCode(code)
                .orElseThrow(() -> new RuntimeException("Process not found: " + code));

        return new ProcessDto(
                process.getProcessCode(),
                process.getExplanation()
        );
    }
}
