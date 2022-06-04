package com.videouploadchallenge.controller;

import com.videouploadchallenge.config.ApiResponse;
import com.videouploadchallenge.exception.ApiException;
import com.videouploadchallenge.exception.InvalidFileFormatException;
import com.videouploadchallenge.exception.FilesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class VideoExceptionController {
    @ExceptionHandler
    public ResponseEntity<ApiException> handleVideoExceptions(InvalidFileFormatException exception){
        ApiException apiException = new ApiException(ApiResponse.UNSUPPORTED_MEDIA_TYPE_MESSAGE);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(apiException);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleVideoFileNotExceptions(FilesNotFoundException exception){
        ApiException apiException = new ApiException(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiException);
    }

    @ExceptionHandler
    public ResponseEntity<ApiException> handleUnknownExceptions(Exception exception){
        ApiException apiException = new ApiException(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiException);
    }
}
