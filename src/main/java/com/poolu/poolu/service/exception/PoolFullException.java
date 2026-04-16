package com.poolu.poolu.service.exception;

public class PoolFullException extends RuntimeException {
    public PoolFullException(String message) {
        super(message);
    }
}
