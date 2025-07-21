package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllRoomsSortedByTypeAction implements Action {
    private final ManagerHotel manager;

    public getAllRoomsSortedByTypeAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nНомера (сортировка по типу):");
        manager.getRooms(SortType.TYPE, false)
                .forEach(System.out::println);
    }
}