package com.workintech.spring17challenge.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException apiException) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(apiException.getHttpStatus().value(), apiException.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(apiErrorResponse, apiException.getHttpStatus());
    }


    @ExceptionHandler
    public ResponseEntity<ApiErrorResponse> handleException(Exception exception) {
        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
    }


}
