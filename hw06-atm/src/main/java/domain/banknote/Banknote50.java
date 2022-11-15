package domain.banknote;

public class Banknote50 implements Banknote {
    private final int value;

    public Banknote50() {
        this.value = 50;
    }

    @Override
    public int getValue() {
        return value;
    }
}
