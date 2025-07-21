package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import java.util.Scanner;

public class checkRoomAvailabilityAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(checkRoomAvailabilityAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public checkRoomAvailabilityAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        try {
            System.out.print("\nПроверка номера\nВведите номер: ");
            int number = scanner.nextInt();
            scanner.nextLine();

            boolean isAvailable = manager.isRoomAvailable(number);
            logger.info("Проверка доступности номера {}: {}", number, isAvailable ? "Свободен" : "Занят");
            System.out.println(isAvailable ? "Свободен" : "Занят");
        } catch (Exception e) {
            logger.error("Ошибка при проверке доступности номера: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}