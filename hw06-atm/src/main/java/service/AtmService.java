package service;

import domain.Atm;
import domain.banknote.Banknote;
import exception.SumNotAvailableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AtmService {

    public void addMoney(Atm atm, List<Banknote> banknoteList) {
        for (Banknote banknote: banknoteList) {
            atm.getStorage().get(banknote.getValue()).add(banknote);
        }
    }

    public List<Banknote> getMoney(Atm atm, double sum) {
        int[] banknoteValueArray = new int[]{5000, 2000, 1000, 500, 200, 100, 50};

        List<Banknote> banknoteToGetList = new ArrayList<>();
        for (int banknoteValue: banknoteValueArray) {
            int count = (int)Math.floor(sum/banknoteValue);
            if (atm.getStorage().get(banknoteValue).size() <= count) {
                count = atm.getStorage().get(banknoteValue).size();
            }
            if (count != 0) {
                sum -= (count * banknoteValue);
                List<Banknote> banknoteList = atm.getStorage().get(banknoteValue);
                for (int i=1; i<=count; i++) {
                    banknoteToGetList.add(banknoteList.get(banknoteList.size()-i));
                }
            }
        }

        if (sum == 0) {
            for (Banknote banknote: banknoteToGetList) {
                atm.getStorage().get(banknote.getValue()).remove(banknote);
            }
            return banknoteToGetList;
        }
        else {
            throw new SumNotAvailableException("Такая сумма не может быть выдана");
        }
    }

    public int getAtmSum(Atm atm) {
        int sum = 0;
        for (Map.Entry<Integer, List<Banknote>> mapEntry: atm.getStorage().entrySet()) {
            for (Banknote banknote: mapEntry.getValue()) {
                sum += banknote.getValue();
            }
        }
        return sum;
    }

}
