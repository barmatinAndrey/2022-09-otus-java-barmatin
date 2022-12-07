package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        return data.stream()
                .sorted(Comparator.comparingInt(o -> Integer.parseInt(o.getName().substring(3))))
                .collect(Collectors.toMap(Measurement::getName, Measurement::getValue, Double::sum, LinkedHashMap::new));
    }
}
