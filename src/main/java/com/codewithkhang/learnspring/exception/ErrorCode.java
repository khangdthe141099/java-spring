package com.codewithkhang.learnspring.exception;

public enum ErrorCode {
    USER_EXISTS(1001, "User already exists"),
    USER_NOT_FOUND(1002, "User not found"),
    INVALID_INPUT(1003, "Invalid input provided"),
    DATABASE_ERROR(1004, "Database error occurred"),
    UNAUTHORIZED_ACCESS(1005, "Unauthorized access attempt"),
    INTERNAL_SERVER_ERROR(1006, "Internal server error"),
    VALIDATION_ERROR(1007, "Validation error occurred"),
    RESOURCE_NOT_FOUND(1008, "Requested resource not found"),
    OPERATION_NOT_SUPPORTED(1009, "Operation not supported"),
    RATE_LIMIT_EXCEEDED(1010, "Rate limit exceeded"),
    SERVICE_UNAVAILABLE(1011, "Service is currently unavailable"),
    TIMEOUT_ERROR(1012, "Request timed out"),
    INVALID_CREDENTIALS(1013, "Invalid credentials provided")
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
