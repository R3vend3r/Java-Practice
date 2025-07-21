package hotelsystem.UI.action.import_export.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.Exception.DataExportException;
import java.util.Scanner;

public class exportClientsCsvAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(exportClientsCsvAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public exportClientsCsvAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало экспорта клиентов в CSV");
        try {
            System.out.println("\n=== Экспорт клиентов в CSV ===");
            System.out.print("Введите путь для сохранения (пример: data/clients_export.csv): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) {
                throw new IllegalArgumentException("Путь не может быть пустым");
            }

            int count = manager.getClientCount();
            manager.exportClientsToCsv(path);
            logger.info("Экспортировано {} клиентов в файл: {}", count, path);
            System.out.println("Успешно экспортировано клиентов: " + count);
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода при экспорте клиентов: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (DataExportException e) {
            logger.error("Ошибка экспорта клиентов: {}", e.getMessage());
            System.err.println("Ошибка экспорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Системная ошибка при экспорте клиентов", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}