package com.istlc.backend.productos_api_jbam.common.exception;

import com.istlc.backend.productos_api_jbam.common.error.ApiError;
import com.istlc.backend.productos_api_jbam.producto.exception.BadRequestException;
import com.istlc.backend.productos_api_jbam.producto.exception.ConflictException;
import com.istlc.backend.productos_api_jbam.producto.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiError> handleConflict(
            ConflictException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        // Esto imprimirá el error completo
        ex.printStackTrace();

        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_ERROR",
                "Error interno: " + ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                "Violación de restricción de datos: " + ex.getMostSpecificCause().getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Datos inválidos");

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                mensaje,
                request.getRequestURI()
        );

        return ResponseEntity.badRequest().body(error);
    }
}
