package com.praesentia.praesentiaapi.exceptions;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    //Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> notValidExceptionHandler(MethodArgumentNotValidException ex, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(ex.getMessage());
        return new ResponseEntity<>(new ErrorMessage(
                message,
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> messageNotReadableHandler(HttpMessageNotReadableException ex) {
        final Pattern ENUM_MSG = Pattern.compile("\\[.+\\]");

        //Handling error enum
        if (ex.getCause() != null && ex.getCause() instanceof InvalidFormatException) {
            Matcher match = ENUM_MSG.matcher(ex.getCause().getMessage());
            if (match.find()) {
                return new ResponseEntity<>(new ErrorMessage(
                        "Roles válidos: " + match.group(0),
                        HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(
                new ErrorMessage(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handlerArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                new ErrorMessage(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handlerSecurityExceptionException(AccessDeniedException ex) {
        return new ResponseEntity<>(
                new ErrorMessage(
                        ex.getMessage(),
                        HttpStatus.FORBIDDEN.value()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorMessage> handlerExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>(
                new ErrorMessage(
                        ex.getMessage(),
                        HttpStatus.UNAUTHORIZED.value()),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handlerRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(
                new ErrorMessage(
                        ex.getMessage(),
                        HttpStatus.BAD_GATEWAY.value()),
                HttpStatus.BAD_GATEWAY);
    }

}