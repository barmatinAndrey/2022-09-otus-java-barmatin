package homework;


import java.util.*;

public class CustomerReverseOrder {
    private final LinkedList<Customer> list = new LinkedList<>();
    private Iterator<Customer> iterator;


    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        list.add(customer);
        iterator = list.descendingIterator();
    }

    public Customer take() {
        return iterator.next();
    }
}
