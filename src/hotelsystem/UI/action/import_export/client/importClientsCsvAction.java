package hotelsystem.UI.action.import_export.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.Exception.DataImportException;
import hotelsystem.model.Client;
import java.util.List;
import java.util.Scanner;

public class importClientsCsvAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(importClientsCsvAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public importClientsCsvAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало импорта клиентов из CSV");
        try {
            System.out.println("\n=== Импорт клиентов из CSV ===");
            System.out.print("Введите путь к файлу (пример: data/clients_import.csv): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) {
                throw new IllegalArgumentException("Путь не может быть пустым");
            }

            List<Client> imported = manager.importClientsFromCsv(path);
            logger.info("Успешно импортировано {} клиентов из файла: {}", imported.size(), path);
            System.out.println("Успешно импортировано клиентов: " + imported.size());
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода при импорте клиентов: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (DataImportException e) {
            logger.error("Ошибка импорта клиентов: {}", e.getMessage());
            System.err.println("Ошибка импорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Системная ошибка при импорте клиентов", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}