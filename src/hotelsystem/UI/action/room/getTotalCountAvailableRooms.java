package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;

public class getTotalCountAvailableRooms implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getTotalCountAvailableRooms.class);
    private final ManagerHotel manager;

    public getTotalCountAvailableRooms(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        int count = manager.getAvailableRoomsCount();
        logger.info("Запрос количества свободных номеров: {}", count);
        System.out.printf("\nСвободных номеров: %d%n", count);
    }
}