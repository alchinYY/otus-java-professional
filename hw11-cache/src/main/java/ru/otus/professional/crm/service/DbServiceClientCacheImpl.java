package ru.otus.professional.crm.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ru.otus.professional.cache.HwCache;
import ru.otus.professional.crm.model.Client;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class DbServiceClientCacheImpl implements DBServiceClient {

    private static final Logger logger = LoggerFactory.getLogger(DbServiceClientCacheImpl.class);

    private final DBServiceClient dbServiceClient;
    private final HwCache<Long, Client> cache;


    @Override
    public Client saveClient(Client client) {
        var clientFromDb = dbServiceClient.saveClient(client);
        cache.put(clientFromDb.getId(), clientFromDb);
        return clientFromDb;
    }

    @Override
    public Optional<Client> getClient(long id) {
        var clientFromCache = cache.get(id);

        if(Objects.nonNull(clientFromCache)) {
            return Optional.of(clientFromCache);
        }
        return dbServiceClient.getClient(id);
    }

    @Override
    public List<Client> findAll() {
        return dbServiceClient.findAll();
    }
}
