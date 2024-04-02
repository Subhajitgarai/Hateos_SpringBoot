package com.hateo.customException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionHanling {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> exceptionHandling(CustomException ex){
        String msg = ex.getMessage();
        return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
    }
}
