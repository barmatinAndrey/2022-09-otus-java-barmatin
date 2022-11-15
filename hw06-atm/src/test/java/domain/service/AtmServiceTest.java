package domain.service;

import domain.Atm;
import domain.banknote.*;
import exception.SumNotAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AtmService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AtmServiceTest {
    private Atm atm;
    private AtmService atmService;

    @BeforeEach
    void setUp() {
        atm = new Atm();
        atmService = new AtmService();
    }

    @Test
    void addMoney() {
        List<Banknote> BanknoteList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            BanknoteList.add(new Banknote50());
            BanknoteList.add(new Banknote100());
            BanknoteList.add(new Banknote200());
            BanknoteList.add(new Banknote500());
            BanknoteList.add(new Banknote1000());
            BanknoteList.add(new Banknote2000());
            BanknoteList.add(new Banknote5000());
        }
        atmService.addMoney(atm, BanknoteList);
        atmService.addMoney(atm, List.of(new Banknote200(), new Banknote1000()));
        assertThat(atmService.getAtmSum(atm)).isEqualTo(45450);
    }

    @Test
    void getMoney1() {
        List<Banknote> BanknoteList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            BanknoteList.add(new Banknote50());
            BanknoteList.add(new Banknote100());
            BanknoteList.add(new Banknote200());
            BanknoteList.add(new Banknote500());
            BanknoteList.add(new Banknote1000());
            BanknoteList.add(new Banknote2000());
            BanknoteList.add(new Banknote5000());
        }
        atmService.addMoney(atm, BanknoteList);
        atmService.getMoney(atm, 36850);
        assertThat(atmService.getAtmSum(atm)).isEqualTo(7400);
        assertThat(atm.getStorage().get(5000).size()).isEqualTo(0);
        assertThat(atm.getStorage().get(2000).size()).isEqualTo(0);
        assertThat(atm.getStorage().get(1000).size()).isEqualTo(4);
        assertThat(atm.getStorage().get(500).size()).isEqualTo(4);
        assertThat(atm.getStorage().get(200).size()).isEqualTo(4);
        assertThat(atm.getStorage().get(100).size()).isEqualTo(4);
        assertThat(atm.getStorage().get(50).size()).isEqualTo(4);
    }

    @Test
    void getMoney2() {
        List<Banknote> BanknoteList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            BanknoteList.add(new Banknote50());
            BanknoteList.add(new Banknote100());
            BanknoteList.add(new Banknote200());
            BanknoteList.add(new Banknote500());
            BanknoteList.add(new Banknote1000());
            BanknoteList.add(new Banknote2000());
            BanknoteList.add(new Banknote5000());
        }
        atmService.addMoney(atm, BanknoteList);
        assertThrowsExactly(SumNotAvailableException.class, () -> atmService.getMoney(atm, 50000));
        assertThat(atmService.getAtmSum(atm)).isEqualTo(44250);
    }

    @Test
    void getMoney3() {
        List<Banknote> BanknoteList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            BanknoteList.add(new Banknote50());
            BanknoteList.add(new Banknote100());
            BanknoteList.add(new Banknote200());
            BanknoteList.add(new Banknote500());
            BanknoteList.add(new Banknote1000());
            BanknoteList.add(new Banknote2000());
            BanknoteList.add(new Banknote5000());
        }
        atmService.addMoney(atm, BanknoteList);
        assertThrowsExactly(SumNotAvailableException.class, () -> atmService.getMoney(atm, 5120));
        assertThat(atmService.getAtmSum(atm)).isEqualTo(44250);
    }

    @Test
    void getAtmSum() {
        List<Banknote> BanknoteList = new ArrayList<>();
        for (int i=0; i<5; i++) {
            BanknoteList.add(new Banknote50());
            BanknoteList.add(new Banknote100());
            BanknoteList.add(new Banknote200());
            BanknoteList.add(new Banknote500());
            BanknoteList.add(new Banknote1000());
            BanknoteList.add(new Banknote2000());
            BanknoteList.add(new Banknote5000());
        }
        atmService.addMoney(atm, BanknoteList);
        assertThat(atmService.getAtmSum(atm)).isEqualTo(44250);
    }

}