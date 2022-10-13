package ru.otus.professional;

import java.util.*;

public class CustomerReverseOrder {

    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    private final Deque<Customer> customers;

    public CustomerReverseOrder() {
        customers = new ArrayDeque<>();
    }

    public void add(Customer customer) {
        customers.addLast(customer);
    }

    public Customer take() {
        return customers.pollLast();
    }
}