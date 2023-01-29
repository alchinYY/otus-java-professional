package ru.otus.professional.service;

import ru.otus.professional.model.Client;

import java.util.List;

public interface ClientService {

    Client createClient(Client client);

    void removeClientById(long id);

    List<Client> getAll();

}
