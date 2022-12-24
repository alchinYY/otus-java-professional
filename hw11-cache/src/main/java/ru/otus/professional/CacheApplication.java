package ru.otus.professional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.professional.cache.MyCache;
import ru.otus.professional.configuration.HibernateConfiguration;
import ru.otus.professional.core.repository.DataTemplateHibernate;
import ru.otus.professional.crm.model.Client;
import ru.otus.professional.crm.service.DBServiceClient;
import ru.otus.professional.crm.service.DbServiceClientCacheImpl;
import ru.otus.professional.crm.service.DbServiceClientImpl;
import ru.otus.professional.listeners.LogListener;

import java.util.WeakHashMap;


public class CacheApplication {

    private static final Logger logger = LoggerFactory.getLogger(CacheApplication.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws InterruptedException{

        var transactionManager = new HibernateConfiguration(HIBERNATE_CFG_FILE)
                .getTransactionManager();

        var listener = new LogListener<Long, Client>();
        var cacheContainer = new WeakHashMap<String, Client>();
        var cache = new MyCache<Long, Client>(cacheContainer);
        cache.addListener(listener);

        var dbServiceClient = new DbServiceClientImpl(transactionManager, new DataTemplateHibernate<>(Client.class));
        var dbServiceClientCache = new DbServiceClientCacheImpl(dbServiceClient, cache);

        var time = System.currentTimeMillis();
        cacheTest(dbServiceClientCache);

        var timeForCache = System.currentTimeMillis();

        cacheTest(dbServiceClient);
        var timeWithoutCache = System.currentTimeMillis();

        logger.info("cache-time = {}", timeForCache - time);
        logger.info("nocache-time = {}", timeWithoutCache - timeForCache);

        System.gc();
        Thread.sleep(1000);

        logger.info("after gc: {}", cacheContainer.size());

        cache.removeListener(listener);
    }

    private static void cacheTest(DBServiceClient dbServiceClient) {
        for(var i = 0; i < 100; i++) {
            var client = dbServiceClient.saveClient(new Client(i + "-client"));
            logger.info("read from without cache - {}", dbServiceClient.getClient(client.getId()));
        }
    }
}
