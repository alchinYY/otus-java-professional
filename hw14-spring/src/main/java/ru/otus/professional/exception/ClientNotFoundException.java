package ru.otus.professional.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends ClientAppException {
    public ClientNotFoundException(String message) {
        super(message);
    }
}
