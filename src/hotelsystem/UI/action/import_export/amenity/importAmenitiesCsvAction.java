package hotelsystem.UI.action.import_export.amenity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.Exception.DataImportException;
import hotelsystem.model.Amenity;
import java.util.List;
import java.util.Scanner;

public class importAmenitiesCsvAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(importAmenitiesCsvAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public importAmenitiesCsvAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало импорта услуг из CSV");
        try {
            System.out.println("\n=== Импорт услуг из CSV ===");
            System.out.print("Введите путь к файлу (пример: data/amenities_import.csv): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) {
                throw new IllegalArgumentException("Путь не может быть пустым");
            }

            List<Amenity> imported = manager.importAmenitiesFromCsv(path);
            logger.info("Успешно импортировано {} услуг из файла: {}", imported.size(), path);
            System.out.println("Успешно импортировано услуг: " + imported.size());
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода при импорте услуг: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (DataImportException e) {
            logger.error("Ошибка импорта услуг: {}", e.getMessage());
            System.err.println("Ошибка импорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Системная ошибка при импорте услуг", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}