package hotelsystem.UI.action.import_export.amenityOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.Exception.DataExportException;
import java.util.Scanner;

public class exportAmenityOrdersCsvAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(exportAmenityOrdersCsvAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public exportAmenityOrdersCsvAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало экспорта заказов услуг в CSV");
        try {
            System.out.println("\n=== Экспорт заказов услуг в CSV ===");
            System.out.print("Введите путь для сохранения (пример: data/amenity_orders_export.csv): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) {
                throw new IllegalArgumentException("Путь не может быть пустым");
            }

            manager.exportAmenityOrdersToCsv(path);
            logger.info("Экспорт заказов услуг успешно завершен в файл: {}", path);
            System.out.println("Экспорт заказов услуг завершен успешно!");
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода при экспорте заказов услуг: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (DataExportException e) {
            logger.error("Ошибка экспорта заказов услуг: {}", e.getMessage());
            System.err.println("Ошибка экспорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Системная ошибка при экспорте заказов услуг", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}