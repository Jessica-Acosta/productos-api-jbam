package com.istlc.backend.productos_api_jbam.producto.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
