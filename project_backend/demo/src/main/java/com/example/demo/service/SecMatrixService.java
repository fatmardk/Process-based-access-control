package com.example.demo.service;

import com.example.demo.model.entity.User;

public interface SecMatrixService {
    boolean hasAccessToProcess(User user, String processCode);
}

