package UI.action.room;

import UI.action.Action;
import enums.SortType;
import Controller.ManagerHotel;

public class getAllAvailableRoomsSortedByPriceAction implements Action {
    private final ManagerHotel manager;

    public getAllAvailableRoomsSortedByPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nСвободные номера (по цене):");
        manager.getRooms(SortType.PRICE, true).values()
                .forEach(r -> System.out.printf("%d - %s (%.2f руб.)%n",
                        r.getNumberRoom(), r.getType(), r.getPriceForDay()));
    }
}