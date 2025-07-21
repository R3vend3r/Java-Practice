package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllRoomsSortedByPriceAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllRoomsSortedByPriceAction.class);
    private final ManagerHotel manager;

    public getAllRoomsSortedByPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос всех номеров, отсортированных по цене");
        var rooms = manager.getRooms(SortType.PRICE, false);
        System.out.println("\nНомера (сортировка по цене):");
        rooms.forEach(r -> System.out.printf("%d - %.2f руб.%n",
                r.getNumberRoom(), r.getPriceForDay()));
        logger.info("Всего номеров: {}", rooms.size());
    }
}