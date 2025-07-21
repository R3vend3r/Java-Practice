package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllRoomsSortedByStarsAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllRoomsSortedByStarsAction.class);
    private final ManagerHotel manager;

    public getAllRoomsSortedByStarsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос всех номеров, отсортированных по звездам");
        var rooms = manager.getRooms(SortType.STARS, false);
        System.out.println("\nНомера (сортировка по звездам):");
        rooms.forEach(r -> System.out.printf("%d - %d★%n",
                r.getNumberRoom(), r.getStars()));
        logger.info("Всего номеров: {}", rooms.size());
    }
}