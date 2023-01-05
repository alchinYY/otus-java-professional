package ru.otus.professional.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.professional.crm.model.Address;
import ru.otus.professional.crm.model.Client;
import ru.otus.professional.crm.model.Phone;
import ru.otus.professional.dao.ClientDao;
import ru.otus.professional.dto.ClientDto;
import ru.otus.professional.mapper.Mapper;
import ru.otus.professional.validator.ClientDtoValidator;
import ru.otus.professional.validator.ValidatorException;

import java.io.IOException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class CreateClientApiServlet extends HttpServlet {

    private final ClientDao clientDao;
    private final Gson gson;
    private final Mapper<ClientDto, Client> mapper;
    private final ClientDtoValidator clientDtoValidator;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        String bodyString = request.getReader().lines().collect(Collectors.joining());
        var body = gson.fromJson(bodyString, ClientDto.class);
        try {
            clientDtoValidator.validate(body, resp);
            clientDao.save(mapper.map(body));
        } catch (ValidatorException ex) {
            log.warn(ex.getMessage());
        }
    }


}
