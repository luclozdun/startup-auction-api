package com.auction.auction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {

    @ExceptionHandler(value = { ResourceNotFoundExceptionRequest.class })
    public ResponseEntity<Object> handlerRequestException(ResourceNotFoundExceptionRequest e) {
        // create Payload
        ResourceNotFoundException exception = new ResourceNotFoundException(e.getMessage(), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

}
