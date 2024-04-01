package com.praesentia.praesentiaapi.auth.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ApiResponse {
    private int status;
    private String message;
}
