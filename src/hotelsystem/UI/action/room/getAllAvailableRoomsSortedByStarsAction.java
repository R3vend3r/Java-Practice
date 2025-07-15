package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

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