package ru.otus.professional;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.professional.jdbc.datasource.DriverManagerDataSource;
import ru.otus.professional.jdbc.mapper.DataTemplateJdbc;
import ru.otus.professional.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus.professional.jdbc.mapper.EntitySQLMetaDataImpl;
import ru.otus.professional.jdbc.model.Client;
import ru.otus.professional.jdbc.model.Manager;
import ru.otus.professional.jdbc.repository.DbExecutorImpl;
import ru.otus.professional.jdbc.service.DbServiceClientImpl;
import ru.otus.professional.jdbc.service.DbServiceManagerImpl;
import ru.otus.professional.jdbc.sessionmanager.TransactionRunnerJdbc;

import javax.sql.DataSource;

public class HomeWork {
    private static final String URL = "jdbc:postgresql://localhost:5430/demoDB";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    private static final Logger log = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
// Общая часть
        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);
        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();

// Работа с клиентом
        var entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        var entitySQLMetaDataClient = new EntitySQLMetaDataImpl<>(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataClient, entityClassMetaDataClient); //реализация DataTemplate, универсальная

// Код дальше должен остаться
        var dbServiceClient = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
        var clientAfterSave = dbServiceClient.saveClient(new Client("dbServiceFirst"));
        log.info(">>>after save::{}", clientAfterSave);
        var clientSecond = dbServiceClient.saveClient(new Client("dbServiceSecond"));
        var clientSecondSelected = dbServiceClient.getClient(clientSecond.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientSecond.getId()));
        log.info("clientSecondSelected:{}", clientSecondSelected);
        log.info(">>>All clients::{}", dbServiceClient.findAll());
        var clientForUpdate = clientSecondSelected;
        clientForUpdate.setName("New name for client");
        var clientAfterUpdate = dbServiceClient.saveClient(clientForUpdate);

        log.info(">>>Last client after update::{}", clientAfterUpdate);
// Сделайте тоже самое с классом Manager (для него надо сделать свою таблицу)

        var entityClassMetaDataManager = new EntityClassMetaDataImpl<>(Manager.class);
        var entitySQLMetaDataManager = new EntitySQLMetaDataImpl<>(entityClassMetaDataManager);
        var dataTemplateManager = new DataTemplateJdbc<>(dbExecutor, entitySQLMetaDataManager, entityClassMetaDataManager);

        var dbServiceManager = new DbServiceManagerImpl(transactionRunner, dataTemplateManager);
        var managerAfterSave = dbServiceManager.saveManager(new Manager("ManagerFirst"));
        log.info(">>>after save::{}", managerAfterSave);

        var managerSecond = dbServiceManager.saveManager(new Manager("ManagerSecond"));
        var managerSecondSelected = dbServiceManager.getManager(managerSecond.getNo())
                .orElseThrow(() -> new RuntimeException("Manager not found, id:" + managerSecond.getNo()));
        log.info("managerSecondSelected:{}", managerSecondSelected);
        log.info(">>>All managers::{}", dbServiceManager.findAll());

        var managerForUpdate = managerSecondSelected;
        managerForUpdate.setLabel("New Label for manager");
        managerForUpdate.setParam1("New param for manager");
        var managerAfterUpdate = dbServiceManager.saveManager(managerForUpdate);
        log.info(">>>Last manager after update::{}", managerAfterUpdate);
    }

    private static void flywayMigrations(DataSource dataSource) {
        log.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/migration")
                .load();
        flyway.migrate();
        log.info("db migration finished.");
        log.info("***");
    }
}