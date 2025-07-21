package hotelsystem.UI.action.import_export.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.Exception.DataImportException;
import hotelsystem.model.Room;
import java.util.List;
import java.util.Scanner;

public class importRoomsCsvAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(importRoomsCsvAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public importRoomsCsvAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало импорта номеров из CSV");
        try {
            System.out.println("\n=== Импорт номеров из CSV ===");
            System.out.print("Введите путь к файлу (например: data/rooms_import.csv): ");
            String filePath = scanner.nextLine().trim();

            if (filePath.isEmpty()) {
                throw new IllegalArgumentException("Путь к файлу не может быть пустым");
            }

            List<Room> importedRooms = manager.importRoomsFromCsv(filePath);
            logger.info("Успешно импортировано {} номеров из файла: {}", importedRooms.size(), filePath);
            System.out.println("\nИмпорт успешно завершен! Загружено номеров: " + importedRooms.size());
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода при импорте номеров: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (DataImportException e) {
            logger.error("Ошибка импорта номеров: {}", e.getMessage());
            System.err.println("Ошибка импорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Системная ошибка при импорте номеров", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}