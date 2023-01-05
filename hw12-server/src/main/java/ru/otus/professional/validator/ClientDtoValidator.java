package ru.otus.professional.validator;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.professional.dto.ClientDto;
import ru.otus.professional.dto.ExceptionBody;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ClientDtoValidator implements Validator<ClientDto> {

    private static final String VALIDATOR_MESSAGE = "This phone-string not correct." +
            "Available empty-string or string with \";\" separator: 00-00-00;01-02-03;";
    private static final int code = 412;
    private final Gson gson;

    @Override
    public void validate(ClientDto clientDto, HttpServletResponse resp) throws ValidatorException {
        try {
            if (!clientDto.getPhonesString().matches(("((([\\d]+[-])+[\\d]+);)*"))) {
                resp.setStatus(code);
                resp.getWriter().print(gson.toJson(new ExceptionBody(VALIDATOR_MESSAGE, ValidatorException.class.getSimpleName())));
                throw new ValidatorException(VALIDATOR_MESSAGE);
            }
        } catch (IOException ex) {
            log.info("ex::", ex);
            resp.setStatus(500);
        }
    }
}
