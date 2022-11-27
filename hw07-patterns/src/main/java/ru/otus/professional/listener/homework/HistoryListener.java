package ru.otus.professional.listener.homework;

import ru.otus.professional.listener.Listener;
import ru.otus.professional.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Message> messagesMap = new HashMap<>();

    @Override
    public void onUpdated(Message message) {
        System.out.println("Добавлено на хранение сообщение с id " + message.getId());
        messagesMap.put(message.getId(), message.toBuilder().build());
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messagesMap.getOrDefault(id, null));
    }


}
