package com.istlc.backend.productos_api_jbam.producto.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
