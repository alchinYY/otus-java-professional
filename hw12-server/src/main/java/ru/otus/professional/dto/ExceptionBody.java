package ru.otus.professional.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ExceptionBody {

    private final String message;
    private final String exceptionClass;

}
