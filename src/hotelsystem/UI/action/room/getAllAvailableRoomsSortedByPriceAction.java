package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllAvailableRoomsSortedByPriceAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllAvailableRoomsSortedByPriceAction.class);
    private final ManagerHotel manager;

    public getAllAvailableRoomsSortedByPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос свободных номеров, отсортированных по цене");
        var rooms = manager.getRooms(SortType.PRICE, true);
        System.out.println("\nСвободные номера (по цене):");
        rooms.forEach(r -> System.out.printf("%d - %s (%.2f руб.)%n",
                r.getNumberRoom(), r.getType(), r.getPriceForDay()));
        logger.info("Найдено {} свободных номеров", rooms.size());
    }
}