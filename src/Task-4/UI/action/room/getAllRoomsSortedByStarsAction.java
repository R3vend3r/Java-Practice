package UI.action.room;

import UI.action.Action;
import enums.SortType;
import Controller.ManagerHotel;

public class getAllRoomsSortedByStarsAction implements Action {
    private final ManagerHotel manager;

    public getAllRoomsSortedByStarsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nНомера (сортировка по звездам):");
        manager.getRooms(SortType.STARS, false).values()
                .forEach(r -> System.out.printf("%d - %d★%n",
                        r.getNumberRoom(), r.getStars()));
    }
}