package ru.otus.professional.dao;

import ru.otus.professional.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientDao {

    Optional<Client> findById(long id);

    List<Client> getAll();

    Client save(Client client);

}
