package homework;


import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> map = new TreeMap<>(new Comparator<Customer>() {
        @Override
        public int compare(Customer o1, Customer o2) {
            return Long.compare(o1.getScores(), o2.getScores());
        }
    });


    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = map.firstEntry();
        if (entry != null) {
            Customer customerCopy = new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores());
            return Map.entry(customerCopy, entry.getValue());
        }
        else {
            return null;
        }
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = map.higherEntry(customer);
        if (entry != null) {
            Customer customerCopy = new Customer(entry.getKey().getId(), entry.getKey().getName(), entry.getKey().getScores());
            return Map.entry(customerCopy, entry.getValue());
        }
        else {
            return null;
        }
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
