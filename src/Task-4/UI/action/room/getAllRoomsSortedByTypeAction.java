package UI.action.room;

import UI.action.Action;
import enums.SortType;
import Controller.ManagerHotel;

public class getAllRoomsSortedByTypeAction implements Action {
    private final ManagerHotel manager;

    public getAllRoomsSortedByTypeAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nНомера (сортировка по типу):");
        manager.getRooms(SortType.TYPE, false).values()
                .forEach(System.out::println);
    }
}