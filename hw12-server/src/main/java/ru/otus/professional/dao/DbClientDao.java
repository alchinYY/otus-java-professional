package ru.otus.professional.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.professional.crm.model.Client;
import ru.otus.professional.crm.service.DBServiceClient;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DbClientDao implements ClientDao {

    private final DBServiceClient dbServiceClient;

    @Override
    public Optional<Client> findById(long id) {
        return dbServiceClient.getClient(id);
    }

    @Override
    public List<Client> getAll() {
        return dbServiceClient.findAll();
    }

    @Override
    public Client save(Client client) {
        return dbServiceClient.saveClient(client);
    }
}
