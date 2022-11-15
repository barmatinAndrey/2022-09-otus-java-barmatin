package domain.banknote;

public class Banknote200 implements Banknote {
    private final int value;

    public Banknote200() {
        this.value = 200;
    }

    @Override
    public int getValue() {
        return value;
    }
}
