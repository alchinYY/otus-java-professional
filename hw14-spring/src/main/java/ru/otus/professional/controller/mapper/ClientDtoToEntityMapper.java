package ru.otus.professional.controller.mapper;

import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;
import ru.otus.professional.controller.dto.ClientDto;
import ru.otus.professional.model.Address;
import ru.otus.professional.model.Client;
import ru.otus.professional.model.Phone;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class ClientDtoToEntityMapper extends AbstractConverter<ClientDto, Client> {
    @Override
    protected Client convert(ClientDto source) {
        var phones = Arrays.stream(source.getPhonesString().split(";"))
                .map(Phone::new)
                .collect(Collectors.toSet());

        return new Client(source.getName()
                , new Address(source.getAddress())
                , phones
        );
    }
}
