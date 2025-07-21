package hotelsystem.UI.action.import_export.amenityOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.Exception.DataImportException;
import hotelsystem.model.AmenityOrder;
import java.util.List;
import java.util.Scanner;

public class importAmenityOrdersCsvAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(importAmenityOrdersCsvAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public importAmenityOrdersCsvAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало импорта заказов услуг из CSV");
        try {
            System.out.println("\n=== Импорт заказов услуг из CSV ===");
            System.out.print("Введите путь к файлу (пример: data/amenity_orders_import.csv): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) {
                throw new IllegalArgumentException("Путь не может быть пустым");
            }

            List<AmenityOrder> imported = manager.importAmenityOrdersFromCsv(path);
            logger.info("Успешно импортировано {} заказов услуг из файла: {}", imported.size(), path);
            System.out.println("Успешно импортировано заказов: " + imported.size());
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода при импорте заказов услуг: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (DataImportException e) {
            logger.error("Ошибка импорта заказов услуг: {}", e.getMessage());
            System.err.println("Ошибка импорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Системная ошибка при импорте заказов услуг", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}