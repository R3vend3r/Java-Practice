package hotelsystem.UI.action.import_export.booking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.Exception.DataExportException;
import hotelsystem.enums.SortType;
import java.util.Scanner;

public class exportBookingsCsvAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(exportBookingsCsvAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public exportBookingsCsvAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало экспорта бронирований в CSV");
        try {
            System.out.println("\n=== Экспорт бронирований в CSV ===");
            System.out.print("Введите путь для сохранения (пример: data/bookings_export.csv): ");
            String path = scanner.nextLine().trim();

            if (path.isEmpty()) {
                throw new IllegalArgumentException("Путь не может быть пустым");
            }

            int activeCount = manager.getAllActiveBookings(SortType.NONE).size();
            int completedCount = manager.getAllCompletedBookings().size();
            manager.exportRoomBookingsToCsv(path);
            logger.info("Экспортировано {} активных и {} завершенных бронирований в файл: {}",
                    activeCount, completedCount, path);
            System.out.println("Успешно экспортировано бронирований: " + (activeCount + completedCount));
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода при экспорте бронирований: {}", e.getMessage());
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (DataExportException e) {
            logger.error("Ошибка экспорта бронирований: {}", e.getMessage());
            System.err.println("Ошибка экспорта: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Системная ошибка при экспорте бронирований", e);
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}