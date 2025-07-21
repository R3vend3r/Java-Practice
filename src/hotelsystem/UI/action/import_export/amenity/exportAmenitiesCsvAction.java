package hotelsystem.UI.action.import_export.amenity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.Exception.DataExportException;
import hotelsystem.enums.SortType;
import java.util.Scanner;

public class exportAmenitiesCsvAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(exportAmenitiesCsvAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public exportAmenitiesCsvAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало экспорта услуг в CSV");
        try {
            System.out.println("\n=== Экспорт услуг в CSV ===");
            System.out.print("Введите путь для сохранения (пример: data/amenities_export.csv): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) {
                throw new IllegalArgumentException("Путь не может быть пустым");
            }

            int count = manager.getAmenities(SortType.NONE).size();
            manager.exportAmenitiesToCsv(path);
            logger.info("Успешно экспортировано {} услуг в файл: {}", count, path);
            System.out.println("Успешно экспортировано услуг: " + count);
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода при экспорте услуг: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (DataExportException e) {
            logger.error("Ошибка экспорта услуг: {}", e.getMessage());
            System.err.println("Ошибка экспорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Системная ошибка при экспорте услуг", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}