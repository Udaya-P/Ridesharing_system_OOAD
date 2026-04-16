package com.poolu.poolu.service.exception;

public class PoolNotFoundException extends RuntimeException {
    public PoolNotFoundException(String message) {
        super(message);
    }
}
