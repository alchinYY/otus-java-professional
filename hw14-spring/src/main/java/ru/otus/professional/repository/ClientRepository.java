package ru.otus.professional.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.professional.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {


    @Override
    @Query(value = """
            select c.id           as client_id,
                   c.name         as client_name,
                   a.id           as address_id,
                   a.street       as address_street,
                   a.client_id    as address_client_id,
                   ph.id          as phone_id,
                   ph.number      as phone_number
            from client c
                    left outer join phones ph
                                    on ph.client_id = c.id
                    left outer join client_addresses a
                                    on c.id = a.client_id
                 order by client_id
                    """,
            resultSetExtractorClass = ClientResultSetExtractorClass.class)
    List<Client> findAll();

}
