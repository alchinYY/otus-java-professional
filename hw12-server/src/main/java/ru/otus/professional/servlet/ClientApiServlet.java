package ru.otus.professional.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ru.otus.professional.crm.model.Client;
import ru.otus.professional.dao.ClientDao;
import ru.otus.professional.dto.ClientDto;
import ru.otus.professional.mapper.Mapper;

import java.io.IOException;

@Slf4j
public class ClientApiServlet extends HttpServlet {

    private static final int ID_PATH_PARAM_POSITION = 1;

    private final ClientDao clientDao;
    private final Gson gson;
    private final Mapper<Client, ClientDto> mapper;

    public ClientApiServlet(ClientDao clientDao, Gson gson, Mapper<Client, ClientDto> mapper) {
        this.clientDao = clientDao;
        this.gson = gson;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var id = extractIdFromRequest(request);
        var client = clientDao.findById(id).orElse(null);
        log.info("current client with id \"{}\"::{}", id, client);
        var jsonResult = gson.toJson(mapper.map(client));
        log.info("current client as json::{}", jsonResult);

        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream out = response.getOutputStream();
        out.print(jsonResult);
    }

    private long extractIdFromRequest(HttpServletRequest request) {
        String[] path = request.getPathInfo().split("/");
        String id = (path.length > 1)? path[ID_PATH_PARAM_POSITION]: String.valueOf(- 1);
        return Long.parseLong(id);
    }
}
