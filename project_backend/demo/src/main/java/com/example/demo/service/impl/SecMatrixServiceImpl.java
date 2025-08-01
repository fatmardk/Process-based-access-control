package com.example.demo.service.impl;

import com.example.demo.model.entity.User;
import com.example.demo.repository.SecMatrixRepository;
import com.example.demo.service.SecMatrixService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecMatrixServiceImpl implements SecMatrixService {

    private final SecMatrixRepository secMatrixRepository;

    @Override
    @Cacheable(value = "processAccess", key = "#user.id + '-' + #processCode")
    public boolean hasAccessToProcess(User user, String processCode) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return false;
        }

        List<String> roleCodes = user.getRoles().stream()
                .map(userRole -> userRole.getRole().getRoleCode())
                .toList();

        return secMatrixRepository.existsByIdRoleCodeInAndIdProcessCode(roleCodes, processCode);
    }
}

