package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Message> messageMap = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        ObjectForMessage objectForMessage13 = null;
        if (msg.getField13() != null) {
            objectForMessage13 = new ObjectForMessage();
            if (msg.getField13().getData() != null) {
                objectForMessage13.setData(new ArrayList<>(msg.getField13().getData()));
            }
        }
        Message messageCopy = new Message.Builder(msg.getId())
                .field1(msg.getField1())
                .field2(msg.getField2())
                .field3(msg.getField3())
                .field4(msg.getField4())
                .field5(msg.getField5())
                .field6(msg.getField6())
                .field7(msg.getField7())
                .field8(msg.getField8())
                .field9(msg.getField9())
                .field10(msg.getField10())
                .field11(msg.getField11())
                .field12(msg.getField12())
                .field13(objectForMessage13)
                .build();
        messageMap.put(msg.getId(), messageCopy);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(messageMap.get(id));
    }
}
