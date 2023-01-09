package ru.otus.professional.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.professional.dao.ClientDao;
import ru.otus.professional.service.TemplateProcessor;
import ru.otus.professional.service.UserAuthService;
import ru.otus.professional.servlet.AuthorizationFilter;
import ru.otus.professional.servlet.LoginServlet;

import java.util.Arrays;

public class ClientWebServerWithFilterBasedSecurity extends ClientWebServerSimple {

    private final UserAuthService authService;

    public ClientWebServerWithFilterBasedSecurity(int port,
                                                 UserAuthService authService,
                                                 ClientDao clientDao,
                                                 Gson gson,
                                                 TemplateProcessor templateProcessor) {
        super(port, clientDao, gson, templateProcessor);
        this.authService = authService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
