package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.RoomCondition;
import hotelsystem.Controller.ManagerHotel;
import java.util.Scanner;

public class changeRoomStatusAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(changeRoomStatusAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public changeRoomStatusAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало изменения статуса номера");
        try {
            System.out.println("\nСмена статуса:");
            System.out.print("Номер комнаты: ");
            int number = scanner.nextInt();
            System.out.print("Новый статус (1-READY, 2-ON_REPAIR, 3-CLEANING_REQUIRED): ");
            int status = scanner.nextInt();

            RoomCondition newCondition = RoomCondition.values()[status-1];
            manager.updateRoomStatus(number, newCondition);
            logger.info("Статус номера {} изменен на {}", number, newCondition);
            System.out.println("Статус обновлен");
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Неверный статус комнаты");
            System.out.println("Ошибка: неверный статус");
        } catch (Exception e) {
            logger.error("Ошибка при изменении статуса: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.nextLine();
        }
    }
}