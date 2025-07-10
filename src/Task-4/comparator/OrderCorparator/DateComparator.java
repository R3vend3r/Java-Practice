package comparator.OrderCorparator;

import model.Order;

import java.util.Comparator;

public class DateComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return o1.getDateOut().compareTo(o2.getDateOut());
    }
}
