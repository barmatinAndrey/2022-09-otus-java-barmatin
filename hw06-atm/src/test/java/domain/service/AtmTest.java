package domain.service;

import domain.Atm;
import domain.Banknote;
import domain.Storage;
import exception.SumNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AtmTest {
    private Atm atm;

    @BeforeEach
    void setUp() {
        Storage storage = new Storage(new Banknote[]{
                Banknote.value50,
                Banknote.value100,
                Banknote.value200,
                Banknote.value500,
                Banknote.value1000,
                Banknote.value2000,
                Banknote.value5000
        });
        atm = new Atm(storage);
    }

    @Test
    void addMoney() {
        List<Banknote> banknoteList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            banknoteList.add(Banknote.value50);
            banknoteList.add(Banknote.value100);
            banknoteList.add(Banknote.value200);
            banknoteList.add(Banknote.value500);
            banknoteList.add(Banknote.value1000);
            banknoteList.add(Banknote.value2000);
            banknoteList.add(Banknote.value5000);
        }
        atm.addMoney(banknoteList);
        atm.addMoney(List.of(Banknote.value200, Banknote.value1000));
        assertThat(atm.getSum()).isEqualTo(45450);
    }

    @Test
    void getMoney1() {
        List<Banknote> banknoteList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            banknoteList.add(Banknote.value50);
            banknoteList.add(Banknote.value100);
            banknoteList.add(Banknote.value200);
            banknoteList.add(Banknote.value500);
            banknoteList.add(Banknote.value1000);
            banknoteList.add(Banknote.value2000);
            banknoteList.add(Banknote.value5000);
        }
        atm.addMoney(banknoteList);
        atm.getMoney(36850);
        assertThat(atm.getSum()).isEqualTo(7400);
        assertThat(atm.storage().getCellMap().get(5000).size()).isEqualTo(0);
        assertThat(atm.storage().getCellMap().get(2000).size()).isEqualTo(0);
        assertThat(atm.storage().getCellMap().get(1000).size()).isEqualTo(4);
        assertThat(atm.storage().getCellMap().get(500).size()).isEqualTo(4);
        assertThat(atm.storage().getCellMap().get(200).size()).isEqualTo(4);
        assertThat(atm.storage().getCellMap().get(100).size()).isEqualTo(4);
        assertThat(atm.storage().getCellMap().get(50).size()).isEqualTo(4);
    }

    @Test
    void getMoney2() {
        List<Banknote> banknoteList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            banknoteList.add(Banknote.value50);
            banknoteList.add(Banknote.value100);
            banknoteList.add(Banknote.value200);
            banknoteList.add(Banknote.value500);
            banknoteList.add(Banknote.value1000);
            banknoteList.add(Banknote.value2000);
            banknoteList.add(Banknote.value5000);
        }
        atm.addMoney(banknoteList);
        assertThrowsExactly(SumNotAvailableException.class, () -> atm.getMoney(50000));
        assertThat(atm.getSum()).isEqualTo(44250);
    }

    @Test
    void getMoney3() {
        List<Banknote> banknoteList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            banknoteList.add(Banknote.value50);
            banknoteList.add(Banknote.value100);
            banknoteList.add(Banknote.value200);
            banknoteList.add(Banknote.value500);
            banknoteList.add(Banknote.value1000);
            banknoteList.add(Banknote.value2000);
            banknoteList.add(Banknote.value5000);
        }
        atm.addMoney(banknoteList);
        assertThrowsExactly(SumNotAvailableException.class, () -> atm.getMoney(5120));
        assertThat(atm.getSum()).isEqualTo(44250);
    }

    @Test
    void getAtmSum() {
        List<Banknote> banknoteList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            banknoteList.add(Banknote.value50);
            banknoteList.add(Banknote.value100);
            banknoteList.add(Banknote.value200);
            banknoteList.add(Banknote.value500);
            banknoteList.add(Banknote.value1000);
            banknoteList.add(Banknote.value2000);
            banknoteList.add(Banknote.value5000);
        }
        atm.addMoney(banknoteList);
        assertThat(atm.getSum()).isEqualTo(44250);
    }

}