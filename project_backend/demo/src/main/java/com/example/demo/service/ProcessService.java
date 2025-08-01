package com.example.demo.service;

import com.example.demo.model.dto.ProcessDto;

public interface ProcessService {
    ProcessDto getByCode(String code);
}
