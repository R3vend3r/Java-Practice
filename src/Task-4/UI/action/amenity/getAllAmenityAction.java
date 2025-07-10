package UI.action.amenity;

import UI.action.Action;
import enums.SortType;
import Controller.ManagerHotel;

public class getAllAmenityAction implements Action {
    private final ManagerHotel manager;

    public getAllAmenityAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nСписок услуг:");
        manager.getAmenities(SortType.NONE)
                .forEach(a -> System.out.printf("%s - %.2f руб.%n",
                        a.getName(), a.getPrice()));
    }
}