package com.github.vivyteam.url.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.NoResultException;

import static org.springframework.http.ResponseEntity.notFound;

@Slf4j
@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<Object> handleNoResultException(NoResultException ex) {
        log.debug("Not found : {}", ex.getMessage());
        return notFound().build();
    }


}