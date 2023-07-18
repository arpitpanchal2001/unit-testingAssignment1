package com.simform.testing.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private int statusCode;
    private String status;
    private String message;
    private String description;

    public ErrorResponse(int statusCode, String status, String message, String description) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.description = description;
    }
}
