package ru.otus.professional.jdbc.sessionmanager;

import java.sql.Connection;
import java.util.function.Function;

public interface TransactionAction<T> extends Function<Connection, T> {
}
