package domain;

import lombok.Getter;

@Getter
public class Atm {
    private final Storage storage;

    public Atm(Storage storage) {
        this.storage = storage;
    }
}
