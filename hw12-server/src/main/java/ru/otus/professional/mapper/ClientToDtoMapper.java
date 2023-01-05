package ru.otus.professional.mapper;

import ru.otus.professional.crm.model.Client;
import ru.otus.professional.crm.model.Phone;
import ru.otus.professional.dto.ClientDto;

import java.util.Optional;

public class ClientToDtoMapper implements Mapper<Client, ClientDto> {
    @Override
    public ClientDto map(Client client) {
        var dto = new ClientDto();
        dto.setId(client.getId());
        dto.setName(client.getName());

        Optional.ofNullable(client.getAddress())
                .ifPresent(a -> dto.setAddress(a.getStreet()));

        Optional.ofNullable(client.getPhones())
                .ifPresent(phones -> dto.setPhonesString(
                        String.join(";", phones.stream().map(Phone::getNumber).toList())
                ));

        return dto;
    }
}
