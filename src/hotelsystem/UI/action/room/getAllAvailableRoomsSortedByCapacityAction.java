package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllAvailableRoomsSortedByCapacityAction implements Action {
    private final ManagerHotel manager;

    public getAllAvailableRoomsSortedByCapacityAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nСвободные номера (по вместимости):");
        manager.getRooms(SortType.CAPACITY, true)
                .forEach(r -> System.out.printf("%d - %d чел. (%s)%n",
                        r.getNumberRoom(), r.getCapacity(), r.getType()));
    }
}