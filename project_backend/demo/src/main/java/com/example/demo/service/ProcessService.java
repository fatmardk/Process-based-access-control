package com.example.demo.service;

import com.example.demo.dto.ProcessDto;

public interface ProcessService {
    ProcessDto getByCode(String code);
}
