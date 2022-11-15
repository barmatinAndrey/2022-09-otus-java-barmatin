package domain;

import domain.banknote.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class Atm {
    private final HashMap<Integer, List<Banknote>> storage;

    public Atm() {
        storage = new HashMap<>();
        storage.put(50, new ArrayList<>());
        storage.put(100, new ArrayList<>());
        storage.put(200, new ArrayList<>());
        storage.put(500, new ArrayList<>());
        storage.put(1000, new ArrayList<>());
        storage.put(2000, new ArrayList<>());
        storage.put(5000, new ArrayList<>());
    }


}
