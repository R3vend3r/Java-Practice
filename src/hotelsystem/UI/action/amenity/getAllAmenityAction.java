package hotelsystem.UI.action.amenity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllAmenityAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllAmenityAction.class);
    private final ManagerHotel manager;

    public getAllAmenityAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос полного списка услуг");
        try {
            System.out.println("\nСписок услуг:");
            var amenities = manager.getAmenities(SortType.NONE);
            amenities.forEach(a -> System.out.printf("%s - %.2f руб.%n",
                    a.getName(), a.getPrice()));
            logger.info("Получено {} услуг", amenities.size());
        } catch (Exception e) {
            logger.error("Ошибка при получении списка услуг", e);
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}