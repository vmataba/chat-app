package com.tabaapps.chat.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(BaseException exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("statusCode", exception.getStatusCode());
        error.put("message", exception.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(error);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAnyOtherException(Exception exception) {
        Map<String, Object> error = new HashMap<>();
        error.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("message", exception.getMessage());
        return ResponseEntity
                .internalServerError()
                .body(error);

    }
}
