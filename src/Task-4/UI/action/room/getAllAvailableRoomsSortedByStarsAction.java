package UI.action.room;

import UI.action.Action;
import enums.SortType;
import Controller.ManagerHotel;

public class getAllAvailableRoomsSortedByStarsAction implements Action {
    private final ManagerHotel manager;

    public getAllAvailableRoomsSortedByStarsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nСвободные номера (по звездам):");
        manager.getRooms(SortType.STARS, true).values()
                .forEach(r -> System.out.printf("%d - %d★ (%s)%n",
                        r.getNumberRoom(), r.getStars(), r.getType()));
    }
}