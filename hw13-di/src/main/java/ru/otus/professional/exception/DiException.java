package ru.otus.professional.exception;

public class DiException extends RuntimeException {

    public DiException(String message) {
        super(message);
    }

    public DiException(String message, Throwable cause) {
        super(message, cause);
    }
}
