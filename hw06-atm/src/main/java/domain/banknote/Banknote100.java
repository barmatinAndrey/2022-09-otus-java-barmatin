package domain.banknote;

public class Banknote100 implements Banknote {
    private final int value;

    public Banknote100() {
        this.value = 100;
    }

    @Override
    public int getValue() {
        return value;
    }
}