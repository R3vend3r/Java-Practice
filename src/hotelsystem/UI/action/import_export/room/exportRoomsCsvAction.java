package hotelsystem.UI.action.import_export.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.Exception.DataExportException;
import java.util.Scanner;

public class exportRoomsCsvAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(exportRoomsCsvAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public exportRoomsCsvAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало экспорта номеров в CSV");
        try {
            System.out.println("\n=== Экспорт номеров в CSV ===");
            System.out.print("Введите путь для сохранения файла (например: data/rooms_export.csv): ");
            String filePath = scanner.nextLine().trim();

            if (filePath.isEmpty()) {
                throw new IllegalArgumentException("Путь к файлу не может быть пустым");
            }

            manager.exportRoomsToCsv(filePath);
            logger.info("Экспорт номеров успешно завершен. Файл: {}", filePath);
            System.out.println("\nЭкспорт успешно завершен! Файл сохранен: " + filePath);
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода при экспорте номеров: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (DataExportException e) {
            logger.error("Ошибка экспорта номеров: {}", e.getMessage());
            System.err.println("Ошибка экспорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Системная ошибка при экспорте номеров", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}