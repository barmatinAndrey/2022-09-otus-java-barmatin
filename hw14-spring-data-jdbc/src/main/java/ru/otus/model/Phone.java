package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@AllArgsConstructor
@Getter
@Table(name = "phone")
public class Phone {
    @Id
    private final Long id;
    private final String number;
}
