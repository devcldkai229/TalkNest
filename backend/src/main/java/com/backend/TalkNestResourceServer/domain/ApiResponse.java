package com.backend.TalkNestResourceServer.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiResponse<T> {

    private int statusCode;

    private String message;

    private T data;

    private LocalDateTime responseAt;
}
