package hotelsystem.UI.action.amenity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllAmenitiesSortedByPriceAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllAmenitiesSortedByPriceAction.class);
    private final ManagerHotel manager;

    public getAllAmenitiesSortedByPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос списка услуг, отсортированных по цене");
        try {
            System.out.println("\nУслуги (по цене):");
            var amenities = manager.getAmenities(SortType.PRICE);
            amenities.forEach(a -> System.out.printf("%.2f руб. - %s%n",
                    a.getPrice(), a.getName()));
            logger.info("Получено {} услуг", amenities.size());
        } catch (Exception e) {
            logger.error("Ошибка при получении списка услуг", e);
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}