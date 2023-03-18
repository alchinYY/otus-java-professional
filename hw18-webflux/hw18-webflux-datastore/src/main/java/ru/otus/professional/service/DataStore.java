package ru.otus.professional.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.professional.domain.Message;

public interface DataStore {

    Mono<Message> saveMessage(Message message);

    Flux<Message> loadMessages(String roomId);

    Flux<Message> findAll();
}
