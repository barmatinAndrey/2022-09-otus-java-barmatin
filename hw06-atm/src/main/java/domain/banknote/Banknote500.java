package domain.banknote;

public class Banknote500 implements Banknote {
    private final int value;

    public Banknote500() {
        this.value = 500;
    }
    @Override
    public int getValue() {
        return value;
    }
}
