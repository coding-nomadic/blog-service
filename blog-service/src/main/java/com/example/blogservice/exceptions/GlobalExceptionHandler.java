package com.example.blogservice.exceptions;

import com.example.blogservice.models.ErrorDetails;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                    WebRequest webRequest) {
        log.info(exception.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getLocalizedMessage(),
                                        webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllException(Exception exception, WebRequest webRequest) {
        log.info(exception.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getLocalizedMessage(),
                                        webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(BlogServiceException.class)
    public ResponseEntity<ErrorDetails> handleBlogException(BlogServiceException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getLocalizedMessage(),
                                        webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // handle specific exceptions
    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ErrorDetails> handleMethodNotFoundException(ResourceNotFoundException exception,
                                    WebRequest webRequest) {
        log.info(exception.getLocalizedMessage());
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getLocalizedMessage(),
                                        webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                    HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info(ex.getLocalizedMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
