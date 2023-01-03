package ru.otus.professional.configuration;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Configuration;
import ru.otus.professional.core.repository.HibernateUtils;
import ru.otus.professional.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.professional.crm.db.migration.MigrationsExecutorFlyway;
import ru.otus.professional.crm.model.Address;
import ru.otus.professional.crm.model.Client;
import ru.otus.professional.crm.model.Phone;

@RequiredArgsConstructor
public class HibernateConfiguration {

    private final String configFile;

    public TransactionManagerHibernate getTransactionManager() {
        var configuration = new Configuration().configure(configFile);
        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");
        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        return new TransactionManagerHibernate(sessionFactory);
    }

}
