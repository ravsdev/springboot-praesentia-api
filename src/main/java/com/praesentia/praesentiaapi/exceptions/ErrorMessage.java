package com.praesentia.praesentiaapi.exceptions;

public class ErrorMessage {
    private int statusCode;
    private String message;

    public ErrorMessage(String message,int statusCode) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

}