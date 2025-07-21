package hotelsystem.UI.action.amenity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import java.util.Scanner;

public class updateAmenityPriceAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(updateAmenityPriceAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public updateAmenityPriceAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало обновления цены услуги");
        try {
            System.out.println("\nИзменение цены услуги:");
            System.out.print("Название услуги: ");
            String name = scanner.nextLine();
            System.out.print("Новая цена: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            manager.updateAmenityPrice(name, price);
            logger.info("Цена услуги '{}' обновлена на {} руб.", name, price);
            System.out.println("Цена обновлена");
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка при обновлении цены: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при обновлении цены", e);
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}