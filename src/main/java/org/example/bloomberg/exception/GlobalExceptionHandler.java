package org.example.bloomberg.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.bloomberg.dto.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<ErrorDetail> errorDetails = fieldErrors.stream()
                .map(error -> {

                    String fullPath = error.getField();

                    return ErrorDetail.builder()
                            .objectName(error.getObjectName())
                            .fieldName(fullPath)
                            .rejectedValue(String.valueOf(error.getRejectedValue()))
                            .message(error.getDefaultMessage())
                            .build();
                })
                .collect(Collectors.toList());
        log.warn("Input validation failed: {}", errorDetails);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);

    }

}
