package domain.banknote;

public class Banknote2000 implements Banknote {
    private final int value;

    public Banknote2000() {
        this.value = 2000;
    }

    @Override
    public int getValue() {
        return value;
    }
}
