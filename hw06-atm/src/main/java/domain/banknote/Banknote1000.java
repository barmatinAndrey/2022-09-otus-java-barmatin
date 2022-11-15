package domain.banknote;

public class Banknote1000 implements Banknote {
    private final int value;

    public Banknote1000() {
        this.value = 1000;
    }

    @Override
    public int getValue() {
        return value;
    }
}
