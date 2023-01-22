package ru.otus.professional.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.professional.exception.ClientNotFoundException;
import ru.otus.professional.model.Client;
import ru.otus.professional.repository.ClientRepository;
import ru.otus.professional.service.ClientService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientDbService implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    @Transactional
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public void removeClientById(long id) {
        var client = clientRepository
                .findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client with id not " + id + " found"));
        clientRepository.delete(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

}
