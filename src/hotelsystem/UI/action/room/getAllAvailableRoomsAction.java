package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllAvailableRoomsAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllAvailableRoomsAction.class);
    private final ManagerHotel managerHotel;

    public getAllAvailableRoomsAction(ManagerHotel managerHotel) {
        this.managerHotel = managerHotel;
    }

    @Override
    public void execute() {
        logger.info("Запрос списка свободных номеров");
        var rooms = managerHotel.getRooms(SortType.NONE, true);

        System.out.println("\n=== СПИСОК СВОБОДНЫХ НОМЕРОВ ===");
        System.out.println("Доступно номеров: " + rooms.size());
        System.out.println("----------------------------------");

        if (rooms.isEmpty()) {
            logger.info("Свободных номеров нет");
            System.out.println("Свободных номеров нет");
        } else {
            rooms.forEach(System.out::println);
        }

        System.out.println("----------------------------------");
    }
}