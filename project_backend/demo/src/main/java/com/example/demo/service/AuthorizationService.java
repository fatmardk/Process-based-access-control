package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.SecMatrixRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final SecMatrixRepository secMatrixRepository;

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
