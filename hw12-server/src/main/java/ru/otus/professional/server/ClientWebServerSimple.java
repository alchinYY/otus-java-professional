package ru.otus.professional.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.professional.dao.ClientDao;
import ru.otus.professional.helpers.FileSystemHelper;
import ru.otus.professional.mapper.ClientToDtoMapper;
import ru.otus.professional.mapper.ClientToEntityMapper;
import ru.otus.professional.service.TemplateProcessor;
import ru.otus.professional.servlet.ClientApiServlet;
import ru.otus.professional.servlet.ClientServlet;
import ru.otus.professional.servlet.CreateClientApiServlet;
import ru.otus.professional.validator.ClientDtoValidator;

public class ClientWebServerSimple implements ClientWebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final ClientDao clientDao;
    private final Gson gson;
    protected final TemplateProcessor templateProcessor;
    private final Server server;

    public ClientWebServerSimple(int port, ClientDao clientDao, Gson gson, TemplateProcessor templateProcessor) {
        this.clientDao = clientDao;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        server = new Server(port);
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/client", "/api/client/*"));


        server.setHandler(handlers);
        return server;
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String ...paths) {
        return servletContextHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new ClientServlet(clientDao, templateProcessor)), "/client");
        servletContextHandler.addServlet(new ServletHolder(new ClientApiServlet(clientDao, gson, new ClientToDtoMapper())), "/api/client/*");
        servletContextHandler.addServlet(createCreateClientApiServlet(), "/api/client");
        return servletContextHandler;
    }

    private ServletHolder createCreateClientApiServlet() {
        return new ServletHolder(new CreateClientApiServlet(clientDao, gson, new ClientToEntityMapper(), new ClientDtoValidator(gson)));
    }

}