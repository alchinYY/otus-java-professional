package ru.otus.professional.exception;

public class TestFrameworkException extends RuntimeException {

    public TestFrameworkException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestFrameworkException(String message) {
        super(message);
    }
}
