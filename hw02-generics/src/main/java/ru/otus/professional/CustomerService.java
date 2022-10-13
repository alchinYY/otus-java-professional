package ru.otus.professional;

import java.util.*;

public class CustomerService {
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private final TreeMap<Customer, String> customersMap;

    public CustomerService() {
        customersMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }

    public Map.Entry<Customer, String> getSmallest() {
        return new CustomerEntry(customersMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = customersMap.higherEntry(customer);
        if(Objects.nonNull(entry)) {
            entry = new CustomerEntry(entry);
        }
        return entry;
    }

    public void add(Customer customer, String data) {
        customersMap.put(customer, data);
    }

}
