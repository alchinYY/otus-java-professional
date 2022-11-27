package ru.otus.professional.handler;

import ru.otus.professional.listener.Listener;
import ru.otus.professional.model.Message;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);
    void removeListener(Listener listener);
}