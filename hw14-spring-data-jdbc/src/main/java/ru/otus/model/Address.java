package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Getter
@AllArgsConstructor
@Table("address")
public class Address {
    @Id
    private final Long id;
    private final String street;
}
