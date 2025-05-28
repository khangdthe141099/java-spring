package com.codewithkhang.learnspring.exception;

import com.codewithkhang.learnspring.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException ex) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setMessage("An error occurred: " + ex.getMessage());
        apiResponse.setCode(1000);

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleRuntimeException(AppException ex) {
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setMessage("An error occurred: " + ex.getErrorCode().getMessage());
        apiResponse.setCode(ex.getErrorCode().getCode());

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleValidationException(MethodArgumentNotValidException ex) {
        ApiResponse apiResponse = new ApiResponse();
        StringBuilder errorMessage = new StringBuilder("Validation failed: ");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ")
        );
        apiResponse.setMessage(errorMessage.toString());
        apiResponse.setCode(1001);
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
