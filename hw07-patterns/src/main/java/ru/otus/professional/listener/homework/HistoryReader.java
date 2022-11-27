package ru.otus.professional.listener.homework;

import ru.otus.professional.model.Message;

import java.util.Optional;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
