package hotelsystem.UI.action.amenity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllAmenitiesSortedByNameAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllAmenitiesSortedByNameAction.class);
    private final ManagerHotel manager;

    public getAllAmenitiesSortedByNameAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос списка услуг, отсортированных по названию");
        try {
            System.out.println("\nУслуги (по названию):");
            var amenities = manager.getAmenities(SortType.ALPHABET);
            amenities.forEach(a -> System.out.printf("%s - %.2f руб.%n",
                    a.getName(), a.getPrice()));
            logger.info("Получено {} услуг", amenities.size());
        } catch (Exception e) {
            logger.error("Ошибка при получении списка услуг", e);
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}