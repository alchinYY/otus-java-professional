package ru.otus.professional.controller.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.otus.professional.controller.dto.ClientDto;
import ru.otus.professional.model.Client;
import ru.otus.professional.model.Phone;

import java.util.Optional;

@Component
public class ClientEntityToDtoMapper extends AbstractConverter<Client, ClientDto> {
    @Override
    protected ClientDto convert(Client source) {
        var dto = new ClientDto();
        dto.setId(source.getId());
        dto.setName(source.getName());

        Optional.ofNullable(source.getAddress())
                .ifPresent(a -> dto.setAddress(a.getStreet()));

        Optional.ofNullable(source.getPhones())
                .ifPresent(phones -> dto.setPhonesString(
                        String.join(";", phones.stream().map(Phone::getNumber).toList())
                ));

        return dto;
    }
}
