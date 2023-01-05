package ru.otus.professional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.otus.professional.cache.MyCache;
import ru.otus.professional.configuration.HibernateConfiguration;
import ru.otus.professional.core.repository.DataTemplateHibernate;
import ru.otus.professional.crm.model.Client;
import ru.otus.professional.crm.service.DbServiceClientCacheImpl;
import ru.otus.professional.crm.service.DbServiceClientImpl;
import ru.otus.professional.dao.ClientDao;
import ru.otus.professional.dao.ClientDbDao;
import ru.otus.professional.dao.InMemoryUserDao;
import ru.otus.professional.dao.UserDao;
import ru.otus.professional.listeners.LogListener;
import ru.otus.professional.server.ClientWebServer;
import ru.otus.professional.server.ClientWebServerWithFilterBasedSecurity;
import ru.otus.professional.service.TemplateProcessor;
import ru.otus.professional.service.TemplateProcessorImpl;
import ru.otus.professional.service.UserAuthService;
import ru.otus.professional.service.UserAuthServiceImpl;

import java.util.WeakHashMap;

public class ServerApplication {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";


    public static void main(String[] args) throws Exception {

        var transactionManager = new HibernateConfiguration(HIBERNATE_CFG_FILE)
                .getTransactionManager();

        var listener = new LogListener<Long, Client>();
        var cacheContainer = new WeakHashMap<String, Client>();
        var cache = new MyCache<Long, Client>(cacheContainer);
        cache.addListener(listener);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, new DataTemplateHibernate<>(Client.class));
        var dbServiceClientCache = new DbServiceClientCacheImpl(dbServiceClient, cache);

        UserDao userDao = new InMemoryUserDao();
        ClientDao clientDao = new ClientDbDao(dbServiceClientCache);
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(userDao);

        ClientWebServer usersWebServer = new ClientWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, clientDao, gson, templateProcessor);

        usersWebServer.start();
        usersWebServer.join();
    }

}
