package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllAvailableRoomsSortedByTypeAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllAvailableRoomsSortedByTypeAction.class);
    private final ManagerHotel manager;

    public getAllAvailableRoomsSortedByTypeAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос свободных номеров, отсортированных по типу");
        var rooms = manager.getRooms(SortType.TYPE, true);
        System.out.println("\nСвободные номера (по типу):");
        rooms.forEach(r -> System.out.printf("%d - %s (%.2f руб.)%n",
                r.getNumberRoom(), r.getType(), r.getPriceForDay()));
        logger.info("Найдено {} свободных номеров", rooms.size());
    }
}