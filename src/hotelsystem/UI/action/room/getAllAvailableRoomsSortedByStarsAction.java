package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllAvailableRoomsSortedByStarsAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllAvailableRoomsSortedByStarsAction.class);
    private final ManagerHotel manager;

    public getAllAvailableRoomsSortedByStarsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос свободных номеров, отсортированных по звездам");
        var rooms = manager.getRooms(SortType.STARS, true);
        System.out.println("\nСвободные номера (по звездам):");
        rooms.forEach(r -> System.out.printf("%d - %d★ (%s)%n",
                r.getNumberRoom(), r.getStars(), r.getType()));
        logger.info("Найдено {} свободных номеров", rooms.size());
    }
}