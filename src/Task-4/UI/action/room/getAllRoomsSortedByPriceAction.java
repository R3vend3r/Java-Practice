package UI.action.room;

import UI.action.Action;
import enums.SortType;
import Controller.ManagerHotel;

public class getAllRoomsSortedByPriceAction implements Action {
    private final ManagerHotel manager;

    public getAllRoomsSortedByPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nНомера (сортировка по цене):");
        manager.getRooms(SortType.PRICE, false).values()
                .forEach(r -> System.out.printf("%d - %.2f руб.%n",
                        r.getNumberRoom(), r.getPriceForDay()));
    }
}