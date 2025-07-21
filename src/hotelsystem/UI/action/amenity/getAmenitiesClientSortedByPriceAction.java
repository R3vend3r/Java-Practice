package hotelsystem.UI.action.amenity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;
import java.util.Scanner;

public class getAmenitiesClientSortedByPriceAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAmenitiesClientSortedByPriceAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public getAmenitiesClientSortedByPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос услуг клиента, отсортированных по цене");
        try {
            System.out.print("\nУслуги клиента (по цене)\nНомер комнаты: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            manager.findClientByRoom(roomNumber).ifPresentOrElse(
                    client -> {
                        var amenities = manager.getClientAmenitiesSorted(client, SortType.PRICE);
                        amenities.forEach(a -> System.out.printf("%.2f руб. - %s%n",
                                a.getAmenity().getPrice(), a.getAmenity().getName()));
                        logger.info("Получено {} услуг для клиента в комнате {}", amenities.size(), roomNumber);
                    },
                    () -> {
                        logger.warn("Клиент в комнате {} не найден", roomNumber);
                        System.out.println("Клиент не найден");
                    }
            );
        } catch (Exception e) {
            logger.error("Ошибка при получении услуг клиента", e);
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}