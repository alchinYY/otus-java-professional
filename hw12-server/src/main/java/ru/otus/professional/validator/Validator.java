package ru.otus.professional.validator;

import jakarta.servlet.http.HttpServletResponse;

public interface Validator <T>{

    void validate(T t, HttpServletResponse resp) throws ValidatorException;

}
