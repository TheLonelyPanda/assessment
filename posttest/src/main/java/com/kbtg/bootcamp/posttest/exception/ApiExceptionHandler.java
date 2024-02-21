package com.kbtg.bootcamp.posttest.exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String errorDefaultMessage = error.getDefaultMessage();
            errors.put("errorMessage", errorDefaultMessage);

        });
        ApiErrorResponse errorResponse = new ApiErrorResponse(
                errors.get("errorMessage"), HttpStatus.BAD_REQUEST, ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleValidationExceptions(HttpMessageNotReadableException e) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                "Request wrong", HttpStatus.BAD_REQUEST, ZonedDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({StatusNotFoundTicketException.class})
    public ResponseEntity<Object> handleExceptionNotFoundTicket (StatusNotFoundTicketException e) {

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                e.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
