package ru.otus.professional.listener.homework;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.professional.model.Message;
import ru.otus.professional.model.ObjectForMessage;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class HistoryListenerTest {

    private static final Message DEFAULT_MESSAGE = new Message.Builder(1)
            .field1("field1")
            .build();

    private static final long ID_NOT_FOUND = 1000;

    @InjectMocks
    private HistoryListener historyListener;

    @Test
    void onUpdated() {

        historyListener.onUpdated(DEFAULT_MESSAGE);

        assertThat(historyListener.findMessageById(DEFAULT_MESSAGE.getId()))
                .isPresent()
                .get()
                .extracting(Message::getId)
                .isEqualTo(DEFAULT_MESSAGE.getId());
        assertThat(historyListener.findMessageById(ID_NOT_FOUND))
                .isEmpty();
    }

    @Test
    void listenerTest() {
        //given
        var historyListener = new HistoryListener();

        var id = 100L;
        var data = "33";
        var field13 = new ObjectForMessage();
        var field13Data = new ArrayList<String>();
        field13Data.add(data);
        field13.setData(field13Data);

        var message = new Message.Builder(id)
                .field10("field10")
                .field13(field13)
                .build();

        //when
        historyListener.onUpdated(message);
        message.getField13().setData(new ArrayList<>()); //меняем исходное сообщение
        field13Data.clear(); //меняем исходный список

        //then
        var messageFromHistory = historyListener.findMessageById(id);
        assertThat(messageFromHistory).isPresent();
        assertThat(messageFromHistory.get().getField13().getData()).containsExactly(data);
    }
}