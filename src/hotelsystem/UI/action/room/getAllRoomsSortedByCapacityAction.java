package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllRoomsSortedByCapacityAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllRoomsSortedByCapacityAction.class);
    private final ManagerHotel manager;

    public getAllRoomsSortedByCapacityAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос всех номеров, отсортированных по вместимости");
        var rooms = manager.getRooms(SortType.CAPACITY, false);
        System.out.println("\nНомера (сортировка по вместимости):");
        rooms.forEach(r -> System.out.printf("%d - %d чел.%n",
                r.getNumberRoom(), r.getCapacity()));
        logger.info("Всего номеров: {}", rooms.size());
    }
}