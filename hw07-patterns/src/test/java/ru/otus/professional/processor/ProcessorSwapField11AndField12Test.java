package ru.otus.professional.processor;

import org.junit.jupiter.api.Test;
import ru.otus.professional.model.Message;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ProcessorSwapField11AndField12Test {

    @Test
    void process() {
        var message = new Message.Builder(1)
                .field11("11")
                .field12("12")
                .build();

        var processorSwapper = new ProcessorSwapField11AndField12();
        var newMessage = processorSwapper.process(message);

        assertThat(newMessage.getField11())
                .isEqualTo(message.getField12());

        assertThat(newMessage.getField12())
                .isEqualTo(message.getField11());
    }
}