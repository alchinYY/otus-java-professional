package ru.otus.professional.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.annotation.Nonnull;
import java.util.Set;

@Table("client")
@Data
@EqualsAndHashCode(of = {"id", "name"})
public class Client {

    @Id
    @Column("id")
    private final Long id;

    @Column("name")
    private final String name;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @Nonnull
    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Client(String name, Address address, Set<Phone> phones) {
        this(null, name, address, phones);
    }
}