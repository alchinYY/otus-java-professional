package ru.otus.professional;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class CustomerService {
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private final TreeMap<Customer, String> customersMap;

    public CustomerService() {
        customersMap = new TreeMap<>(new CustomerComparator());
    }

    public Map.Entry<Customer, String> getSmallest() {
        return new CustomerEntry(customersMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return Optional.ofNullable(customersMap.higherEntry(customer))
                .map(CustomerEntry::new)
                .orElse(null);
    }

    public void add(Customer customer, String data) {
        customersMap.put(customer, data);
    }

}
