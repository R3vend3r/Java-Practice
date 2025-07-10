package comparator.AmenityComparator;

import model.Order;

import java.util.Comparator;

public class PriceAmenComparator implements Comparator<Order> {
    @Override
    public int compare(Order o1, Order o2) {
        return Double.compare(o1.getService().getPrice(), o2.getService().getPrice());
    }
}
