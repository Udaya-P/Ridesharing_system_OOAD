package com.poolu.poolu.service.exception;

public class PoolerNotFoundException extends RuntimeException {
    public PoolerNotFoundException(String message) {
        super(message);
    }
}
