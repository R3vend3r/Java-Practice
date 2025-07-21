package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllRoomsSortedByPriceAction implements Action {
    private final ManagerHotel manager;

    public getAllRoomsSortedByPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nНомера (сортировка по цене):");
        manager.getRooms(SortType.PRICE, false)
                .forEach(r -> System.out.printf("%d - %.2f руб.%n",
                        r.getNumberRoom(), r.getPriceForDay()));
    }
}