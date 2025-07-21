package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllRoomsSortedByCapacityAction implements Action {
    private final ManagerHotel manager;

    public getAllRoomsSortedByCapacityAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nНомера (сортировка по вместимости):");
        manager.getRooms(SortType.CAPACITY, false)
                .forEach(r -> System.out.printf("%d - %d чел.%n",
                        r.getNumberRoom(), r.getCapacity()));
    }
}