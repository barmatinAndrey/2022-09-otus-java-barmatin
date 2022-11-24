package domain;

import lombok.Getter;

@Getter
public enum Banknote {
    value50(50),
    value100(100),
    value200(200),
    value500(500),
    value1000(1000),
    value2000(2000),
    value5000(5000);

    private final int value;

    Banknote(int value) {
        this.value = value;
    }
}
