package ru.otus.professional.dto;

import lombok.Data;

@Data
public class ClientDto {

    private Long id;
    private String name;
    private String address;
    private String phonesString;

}
