package hotelsystem.UI.action.import_export.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.Exception.DataImportException;
import hotelsystem.model.RoomBooking;
import java.util.List;
import java.util.Scanner;

public class importBookingsCsvAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(importBookingsCsvAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public importBookingsCsvAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало импорта бронирований из CSV");
        try {
            System.out.println("\n=== Импорт бронирований из CSV ===");
            System.out.print("Введите путь к файлу (пример: data/bookings_import.csv): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) {
                throw new IllegalArgumentException("Путь не может быть пустым");
            }

            List<RoomBooking> imported = manager.importRoomBookingsFromCsv(path);
            logger.info("Успешно импортировано {} бронирований из файла: {}", imported.size(), path);
            System.out.println("Успешно импортировано бронирований: " + imported.size());
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода при импорте бронирований: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (DataImportException e) {
            logger.error("Ошибка импорта бронирований: {}", e.getMessage());
            System.err.println("Ошибка импорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Системная ошибка при импорте бронирований", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}