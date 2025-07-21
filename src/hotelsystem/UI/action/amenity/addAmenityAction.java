package hotelsystem.UI.action.amenity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.model.Amenity;
import java.util.Scanner;

public class addAmenityAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(addAmenityAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public addAmenityAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало добавления новой услуги");
        try {
            System.out.println("\nДобавление услуги:");
            System.out.print("Название: ");
            String name = scanner.nextLine();
            System.out.print("Цена: ");
            double price = scanner.nextDouble();
            scanner.nextLine();

            Amenity amenity = new Amenity(name, price);
            manager.addAmenity(amenity);
            logger.info("Добавлена новая услуга: {} ({} руб.)", name, price);
            System.out.println("Услуга добавлена");
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка валидации при добавлении услуги: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при добавлении услуги", e);
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}