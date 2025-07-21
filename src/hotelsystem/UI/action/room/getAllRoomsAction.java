package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllRoomsAction implements Action {
    private final ManagerHotel manager;

    public getAllRoomsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nВсе номера:");
        manager.getRooms(SortType.NONE, false).forEach(System.out::println);
        System.out.println("Всего: " + manager.getRooms(SortType.NONE, false).size());
    }
}