package ru.otus.professional.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "client_addresses")
@Data
public class Address {

    @Id
    private final Long id;

    @Column("street")
    private final String street;


    @PersistenceCreator
    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    public Address(String street) {
        this(null, street);
    }

}
