package com.slsb.expense.tracker.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    // jwt token expired exception
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrorsExpiredJwtException(ExpiredJwtException ex) {
        List<String> errors = Arrays.asList(ex.getMessage().toString());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // dto field null or empty exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrorsMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // entity unique constraint violation exception
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrorsDataIntegrityViolationException(DataIntegrityViolationException ex) {
        List<String> errors = Arrays.asList(ex.getMessage().toString());

        if (ex.getMessage().contains("email")) {
            errors = Arrays.asList("Email address is already registered.");
        } else if (ex.getMessage().contains("expense_name")) {
            errors = Arrays.asList("Expense with this name is already present.");
        } else if (ex.getMessage().contains("payment_mode_name")) {
            errors = Arrays.asList("Payment Mode with this name is already present.");
        } else if (ex.getMessage().contains("category_name")) {
            errors = Arrays.asList("Category with this name is already present.");
        } else if (ex.getMessage().contains("cashbook_name")) {
            errors = Arrays.asList("Cashbook with this name is already present.");
        } else {
            errors = Arrays.asList("Data already present.");
        }

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // entity not null and not empty violation exception
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrorsConstraintViolationException(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // Illegal Argument Exception
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrorsIllegalArgumentException(IllegalArgumentException ex) {
        List<String> errors = Arrays.asList(ex.getMessage().toString());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrorsNoSuchElementException(NoSuchElementException ex) {
        List<String> errors = Arrays.asList(ex.getMessage().toString());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrorsAccessDeniedException(AccessDeniedException ex) {
        List<String> errors = Arrays.asList(ex.getMessage().toString());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // all exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrorsException(Exception ex) {
        List<String> errors = Arrays.asList(ex.getMessage().toString());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }



}
