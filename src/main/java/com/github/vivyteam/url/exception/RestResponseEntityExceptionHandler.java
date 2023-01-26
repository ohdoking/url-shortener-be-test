package com.github.vivyteam.url.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.http.ResponseEntity.notFound;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(NoUrlException.class)
    public ResponseEntity<Object> handleNoResultException(NoUrlException ex) {
        log.debug("Not found : {}", ex.getMessage());
        return notFound().build();
    }


}