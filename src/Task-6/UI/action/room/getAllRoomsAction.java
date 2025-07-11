package UI.action.room;

import UI.action.Action;
import enums.SortType;
import Controller.ManagerHotel;

public class getAllRoomsAction implements Action {
    private final ManagerHotel manager;

    public getAllRoomsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nВсе номера:");
        manager.getRooms(SortType.NONE, false).values().forEach(System.out::println);
        System.out.println("Всего: " + manager.getRooms(SortType.NONE, false).size());
    }
}