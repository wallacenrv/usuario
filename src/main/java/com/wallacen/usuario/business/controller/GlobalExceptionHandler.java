package com.wallacen.usuario.business.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallacen.usuario.infrastructure.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


//aqui sao as excecoes globais

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e){
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                            HttpServletRequest request){
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildError(HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                request.getRequestURI(),
                "not found"
                ));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflictException(ConflictException e,
                                                                    HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT) // Status de Conflito (409)
                .body(buildError(
                        HttpStatus.CONFLICT.value(), // Passando o valor correto para status
                        e.getMessage(),
                        request.getRequestURI(),
                        "conflict" // O tipo do erro é "conflict"
                ));
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleConflictException(UnauthorizedException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentsException.class)
    public ResponseEntity<String> handleConflictException(IllegalArgumentsException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponseDto buildError(int status, String message, String path, String error) {
        return ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .message(message)
                .status(status)
                .path(path)
                .error(error)
                .build();
    }


}