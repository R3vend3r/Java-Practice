package hotelsystem.UI.action.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;

public class getAllCompletedBookingsAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllCompletedBookingsAction.class);
    private final ManagerHotel manager;

    public getAllCompletedBookingsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос списка завершенных бронирований");
        try {
            System.out.println("\n=== Завершенные бронирования ===");
            var bookings = manager.getAllCompletedBookings();
            bookings.forEach(booking ->
                    System.out.printf("Номер %d - %s (выезд: %s)%n",
                            booking.getRoom().getNumberRoom(),
                            booking.getClient(),
                            booking.getCheckOutDate())
            );
            logger.info("Получено {} завершенных бронирований", bookings.size());
        } catch (Exception e) {
            logger.error("Ошибка при получении завершенных бронирований", e);
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}