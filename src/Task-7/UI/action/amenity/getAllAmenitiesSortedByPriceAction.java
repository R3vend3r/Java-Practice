package UI.action.amenity;

import UI.action.Action;
import enums.SortType;
import Controller.ManagerHotel;

public class getAllAmenitiesSortedByPriceAction implements Action {
    private final ManagerHotel manager;

    public getAllAmenitiesSortedByPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nУслуги (по цене):");
        manager.getAmenities(SortType.PRICE)
                .forEach(a -> System.out.printf("%.2f руб. - %s%n",
                        a.getPrice(), a.getName()));
    }
}