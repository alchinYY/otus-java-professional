package ru.otus.professional;

import java.util.Map;

public class CustomerEntry implements Map.Entry<Customer, String> {
    private final Customer key;
    private String value;

    public CustomerEntry(Map.Entry<Customer, String> entry) {
        Customer customer = entry.getKey();
        this.key = new Customer(customer.getId(), customer.getName(), customer.getScores());
        this.value = entry.getValue();
    }

    @Override
    public Customer getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String setValue(String value) {
        this.value = value;
        return value;
    }
}
