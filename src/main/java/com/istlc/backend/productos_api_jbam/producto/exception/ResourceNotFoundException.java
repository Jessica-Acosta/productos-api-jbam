package com.istlc.backend.productos_api_jbam.producto.exception;

public class ResourceNotFoundException extends RuntimeException
{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
