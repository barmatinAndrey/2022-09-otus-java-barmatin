package domain.banknote;

public class Banknote5000 implements Banknote {
    private final int value;

    public Banknote5000() {
        this.value = 5000;
    }

    @Override
    public int getValue() {
        return value;
    }
}
