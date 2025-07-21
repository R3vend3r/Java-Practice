package hotelsystem.UI.action.amenity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;
import java.util.Scanner;

public class getAmenitiesClientSortedByDateAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAmenitiesClientSortedByDateAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public getAmenitiesClientSortedByDateAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос услуг клиента, отсортированных по дате");
        try {
            System.out.print("\nУслуги клиента (по дате)\nНомер комнаты: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            manager.findClientByRoom(roomNumber).ifPresentOrElse(
                    client -> {
                        var amenities = manager.getClientAmenitiesSorted(client, SortType.DATE_END);
                        amenities.forEach(a -> System.out.printf("%s - %s%n",
                                a.getServiceDate(), a.getAmenity().getName()));
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