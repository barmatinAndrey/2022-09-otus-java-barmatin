package ru.otus.listener.homework;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.otus.handler.ComplexProcessor;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.DateTimeProvider;
import ru.otus.processor.homework.ProcessorEvenSecondException;
import ru.otus.processor.homework.ProcessorSwap11And12;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

class HistoryListenerTest {

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
                .field13(field13)                 //TODO: раскоментировать
                .build();

        //when
        historyListener.onUpdated(message);
        message.getField13().setData(new ArrayList<>()); //меняем исходное сообщение   //TODO: раскоментировать
        field13Data.clear(); //меняем исходный список   //TODO: раскоментировать

        //then
        var messageFromHistory = historyListener.findMessageById(id);
        assertThat(messageFromHistory).isPresent();
        assertThat(messageFromHistory.get().getField13().getData()).containsExactly(data);  //TODO: раскоментировать
    }

    @Test
    void processorEvenSecondExceptionTest() {
        Processor processor = new ProcessorEvenSecondException(() -> LocalDateTime.now().withSecond(10));
        var message = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .field13(new ObjectForMessage())
                .build();

        assertThrowsExactly(RuntimeException.class, () -> processor.process(message));
    }

}