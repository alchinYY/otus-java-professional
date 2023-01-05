package ru.otus.professional.mapper;

import ru.otus.professional.crm.model.Address;
import ru.otus.professional.crm.model.Client;
import ru.otus.professional.crm.model.Phone;
import ru.otus.professional.dto.ClientDto;

import java.util.Arrays;

public class ClientToEntityMapper implements Mapper<ClientDto, Client> {
    @Override
    public Client map(ClientDto clientDto) {
        var client = new Client(clientDto.getName());
        var address = new Address(null, clientDto.getAddress());
        var phones = Arrays.stream(clientDto.getPhonesString().split(";"))
                .map(p -> new Phone(null, p)).toList();
        client.setAddress(address);
        client.setPhones(phones);

        return client;
    }
}
