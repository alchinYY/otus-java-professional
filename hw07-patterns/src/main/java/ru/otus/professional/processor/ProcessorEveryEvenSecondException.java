package ru.otus.professional.processor;

import ru.otus.professional.exception.ProcessorException;
import ru.otus.professional.model.Message;

public class ProcessorEveryEvenSecondException implements Processor {

    private final DateTimeProvider dateTimeProvider;

    public ProcessorEveryEvenSecondException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        if(dateTimeProvider.getDate().getSecond() % 2 == 0) {
            throw new ProcessorException("Сообщение пришло не вовремя");
        }
        return message.toBuilder().build();
    }
}
