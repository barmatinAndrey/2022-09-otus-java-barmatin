package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorEvenSecondException implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public ProcessorEvenSecondException(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        System.out.println("TIME " + dateTimeProvider.getDateTime().getSecond());
        if (dateTimeProvider.getDateTime().getSecond() % 2 == 0) {
            throw new RuntimeException();
        }
        return message;
    }
}
