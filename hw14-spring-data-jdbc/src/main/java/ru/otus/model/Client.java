package ru.otus.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Getter
@AllArgsConstructor
@Table(name = "client")
public class Client {
    @Id
    private final Long id;
    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phoneSet;


    @Override
    public String toString() {
        StringBuilder phones = new StringBuilder();
        phoneSet.forEach(phone -> phones.append(phone.getNumber()).append(", "));
        if (!phones.isEmpty()) {
            phones.delete(phones.length()-2, phones.length());
        }
        return id + ". " + name + ", " + address.getStreet()  + (phones.toString().isEmpty() ? "" : " (" + phones + ")");
    }

}
