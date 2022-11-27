package ru.otus.professional.processor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.professional.exception.ProcessorException;
import ru.otus.professional.model.Message;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessorEveryEvenSecondExceptionTest {

    private static final int EVEN_SECOND = 1;
    private static final int ODD_SECOND = 2;

    private static final Message DEFAULT_MESSAGE = new Message.Builder(1)
            .field1("field1")
            .build();

    @Mock
    private DateTimeProvider dateTimeProvider;

    @InjectMocks
    private ProcessorEveryEvenSecondException processor;

    @Test
    void process_evenSecond() {
        var date = LocalDateTime.of(1, 1,1,1,1,EVEN_SECOND);

        when(dateTimeProvider.getDate()).thenReturn(date);

        var message = processor.process(DEFAULT_MESSAGE);

        assertThat(message).extracting(Message::getId, Message::getField1)
                .containsExactly(DEFAULT_MESSAGE.getId(), DEFAULT_MESSAGE.getField1());

    }

    @Test
    void process_oddSecond() {
        var date = LocalDateTime.of(1, 1,1,1,1,ODD_SECOND);

        when(dateTimeProvider.getDate()).thenReturn(date);

        assertThatExceptionOfType(ProcessorException.class)
                .isThrownBy(() -> processor.process(DEFAULT_MESSAGE));
    }
}