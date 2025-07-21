package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllRoomsAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllRoomsAction.class);
    private final ManagerHotel manager;

    public getAllRoomsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос всех номеров");
        var rooms = manager.getRooms(SortType.NONE, false);
        System.out.println("\nВсе номера:");
        rooms.forEach(System.out::println);
        System.out.println("Всего: " + rooms.size());
        logger.info("Всего номеров: {}", rooms.size());
    }
}