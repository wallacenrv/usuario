package com.wallacen.usuario.infrastructure.exception;

public class IllegalArgumentsException extends RuntimeException {

    public IllegalArgumentsException(String message) {
        super(message);
    }

    public IllegalArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }
}
