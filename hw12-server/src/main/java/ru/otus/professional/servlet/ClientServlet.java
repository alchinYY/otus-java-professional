package ru.otus.professional.servlet;

import lombok.RequiredArgsConstructor;
import ru.otus.professional.dao.ClientDao;
import ru.otus.professional.service.TemplateProcessor;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class ClientServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clientsTemplate";

    private final ClientDao clientDao;
    private final TemplateProcessor templateProcessor;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        var allClients = clientDao.getAll();
        paramsMap.put(TEMPLATE_ATTR_CLIENTS, allClients);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

}