package domain;

import exception.SumNotAvailableException;
import lombok.Getter;

import java.util.*;

@Getter
public class Storage {
    private final HashMap<Integer, List<Banknote>> cellMap;

    public Storage(Banknote[] banknoteArray) {
        cellMap = new HashMap<>();
        for (Banknote banknote: banknoteArray) {
            cellMap.put(banknote.getValue(), new ArrayList<>());
        }
    }

    public List<Banknote> addMoney(List<Banknote> banknoteList) {
        List<Banknote> banknoteToReturnList = new ArrayList<>();
        for (Banknote banknote : banknoteList) {
            List<Banknote> cell = cellMap.get(banknote.getValue());
            if (cell != null) {
                cell.add(banknote);
            } else {
                banknoteToReturnList.add(banknote);
            }
        }
        return banknoteToReturnList;
    }

    public List<Banknote> getMoney(double sum) {
        int[] banknoteValueArray = Arrays.stream(Banknote.values())
                .filter(banknote -> cellMap.get(banknote.getValue()) != null)
                .filter(banknote -> !cellMap.get(banknote.getValue()).isEmpty())
                .sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()))
                .mapToInt(Banknote::getValue)
                .toArray();

        List<Banknote> banknoteToGetList = new ArrayList<>();
        for (int banknoteValue : banknoteValueArray) {
            int count = (int) Math.floor(sum / banknoteValue);
            if (cellMap.get(banknoteValue).size() <= count) {
                count = cellMap.get(banknoteValue).size();
            }
            if (count != 0) {
                sum -= (count * banknoteValue);
                List<Banknote> banknoteList = cellMap.get(banknoteValue);
                for (int i = 1; i <= count; i++) {
                    banknoteToGetList.add(banknoteList.get(banknoteList.size() - i));
                }
            }
        }

        if (sum == 0) {
            for (Banknote banknote : banknoteToGetList) {
                cellMap.get(banknote.getValue()).remove(banknote);
            }
            return banknoteToGetList;
        } else {
            throw new SumNotAvailableException("Такая сумма не может быть выдана");
        }
    }

    public int getSum() {
        int sum = 0;
        for (Map.Entry<Integer, List<Banknote>> mapEntry : cellMap.entrySet()) {
            for (Banknote banknote : mapEntry.getValue()) {
                sum += banknote.getValue();
            }
        }
        return sum;
    }
}
