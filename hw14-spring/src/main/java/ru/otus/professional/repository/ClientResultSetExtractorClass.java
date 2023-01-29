package ru.otus.professional.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import ru.otus.professional.model.Address;
import ru.otus.professional.model.Client;
import ru.otus.professional.model.Phone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClientResultSetExtractorClass implements ResultSetExtractor<List<Client>> {

    @Override
    public List<Client> extractData(ResultSet rs) throws SQLException, DataAccessException {
        var clientList = new ArrayList<Client>();
        String prevClientId = null;
        Client prevClient = null;
        while (rs.next()) {
            var clientId = rs.getString("client_id");
            Client client = null;
            if (prevClientId == null || !prevClientId.equals(clientId)) {

                client = new Client(
                        Long.parseLong(clientId)
                        , rs.getString("client_name")
                        , readAddress(rs)
                        , new HashSet<>()
//                        , new ArrayList<>()
                );
                prevClientId = clientId;
                clientList.add(client);
                readPhones(rs, client);
                prevClient = client;
            } else {
                readPhones(rs, prevClient);
            }
        }
        return clientList;
    }

    private Address readAddress(ResultSet rs) throws SQLException {
        Address address = null;
        var addressId = (Long) rs.getObject("address_id");
        if(addressId != null) {
            address = new Address(addressId, rs.getString("address_street"));
        }
        return address;
    }

    private void readPhones(ResultSet rs, Client client) throws SQLException {
        Long phoneId = (Long) rs.getObject("phone_id");
        if (client !=  null && phoneId != null) {
            client.getPhones().add(
                    new Phone(phoneId, rs.getString("phone_number")));
        }
    }

}
