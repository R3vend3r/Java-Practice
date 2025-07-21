package hotelsystem.UI.action.amenity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import java.util.Date;
import java.util.Scanner;

public class addAmenityToClientAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(addAmenityToClientAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public addAmenityToClientAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало добавления услуги клиенту");
        try {
            System.out.println("\nДобавление услуги клиенту:");
            System.out.print("Номер комнаты: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Название услуги: ");
            String amenityName = scanner.nextLine();

            var amenity = manager.findAmenityByName(amenityName)
                    .orElseThrow(() -> new IllegalArgumentException("Услуга не найдена"));

            manager.addAmenityToClient(roomNumber, amenity, new Date());
            logger.info("Услуга '{}' добавлена клиенту в комнате {}", amenityName, roomNumber);
            System.out.println("Услуга добавлена");
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка при добавлении услуги клиенту: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при добавлении услуги клиенту", e);
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}