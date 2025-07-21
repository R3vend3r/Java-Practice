package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllRoomsSortedByTypeAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllRoomsSortedByTypeAction.class);
    private final ManagerHotel manager;

    public getAllRoomsSortedByTypeAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос всех номеров, отсортированных по типу");
        var rooms = manager.getRooms(SortType.TYPE, false);
        System.out.println("\nНомера (сортировка по типу):");
        rooms.forEach(System.out::println);
        logger.info("Всего номеров: {}", rooms.size());
    }
}