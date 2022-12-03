package ru.otus.professional.processor;

import ru.otus.professional.model.Message;

public class ProcessorSwapField11AndField12 implements Processor{
    @Override
    public Message process(Message message) {
        var temp = message.getField11();

        return message.toBuilder()
                .field11(message.getField12())
                .field12(temp)
                .build();
    }
}
