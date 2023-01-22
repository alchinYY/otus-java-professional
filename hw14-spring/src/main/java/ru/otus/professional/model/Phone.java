package ru.otus.professional.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "phones")
@Data
public class Phone {

    @Id
    private final Long id;

    @Column("number")
    private final String number;

    @PersistenceCreator
    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    public Phone(String number) {
        this(null, number);
    }
}
