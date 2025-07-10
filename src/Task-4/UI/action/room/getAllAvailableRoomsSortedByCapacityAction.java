package UI.action.room;

import UI.action.Action;
import enums.SortType;
import Controller.ManagerHotel;

public class getAllAvailableRoomsSortedByCapacityAction implements Action {
    private final ManagerHotel manager;

    public getAllAvailableRoomsSortedByCapacityAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nСвободные номера (по вместимости):");
        manager.getRooms(SortType.CAPACITY, true).values()
                .forEach(r -> System.out.printf("%d - %d чел. (%s)%n",
                        r.getNumberRoom(), r.getCapacity(), r.getType()));
    }
}