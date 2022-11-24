package domain;

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

}
