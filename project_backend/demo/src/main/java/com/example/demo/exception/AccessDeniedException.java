package com.example.demo.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String processCode) {
        super("Access denied to process: " + processCode);
    }
}

