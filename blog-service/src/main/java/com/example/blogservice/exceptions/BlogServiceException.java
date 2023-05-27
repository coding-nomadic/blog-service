package com.example.blogservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class BlogServiceException extends RuntimeException {


    public BlogServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlogServiceException(String message, String errorCode) {
        super(message);
    }
}
