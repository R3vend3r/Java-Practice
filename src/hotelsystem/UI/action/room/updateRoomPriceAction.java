package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import java.util.Scanner;

public class updateRoomPriceAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(updateRoomPriceAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public updateRoomPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало обновления цены номера");
        try {
            System.out.println("\nИзменение цены:");
            System.out.print("Номер комнаты: ");
            int number = scanner.nextInt();
            System.out.print("Новая цена: ");
            double price = scanner.nextDouble();

            manager.updateRoomPrice(number, price);
            logger.info("Цена номера {} обновлена на {}", number, price);
            System.out.println("Цена обновлена");
        } catch (Exception e) {
            logger.error("Ошибка при обновлении цены номера: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.nextLine();
        }
    }
}