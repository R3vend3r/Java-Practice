package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllAvailableRoomsSortedByCapacityAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllAvailableRoomsSortedByCapacityAction.class);
    private final ManagerHotel manager;

    public getAllAvailableRoomsSortedByCapacityAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос свободных номеров, отсортированных по вместимости");
        var rooms = manager.getRooms(SortType.CAPACITY, true);
        System.out.println("\nСвободные номера (по вместимости):");
        rooms.forEach(r -> System.out.printf("%d - %d чел. (%s)%n",
                r.getNumberRoom(), r.getCapacity(), r.getType()));
        logger.info("Найдено {} свободных номеров", rooms.size());
    }
}