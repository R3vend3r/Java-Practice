package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllAvailableRoomsSortedByTypeAction implements Action {
    private final ManagerHotel manager;

    public getAllAvailableRoomsSortedByTypeAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nСвободные номера (по типу):");
        manager.getRooms(SortType.TYPE, true).values()
                .forEach(r -> System.out.printf("%d - %s (%.2f руб.)%n",
                        r.getNumberRoom(), r.getType(), r.getPriceForDay()));
    }
}