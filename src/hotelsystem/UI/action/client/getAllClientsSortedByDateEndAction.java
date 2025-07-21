package hotelsystem.UI.action.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllClientsSortedByDateEndAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllClientsSortedByDateEndAction.class);
    private final ManagerHotel manager;

    public getAllClientsSortedByDateEndAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало получения клиентов, отсортированных по дате выезда");
        try {
            System.out.println("\nКлиенты (по дате выезда):");
            manager.getAllActiveBookings(SortType.DATE_END)
                    .forEach(c -> System.out.printf("%s %s (выезд: %s)%n",
                            c.getClient().getName(), c.getClient().getSurname(), c.getCheckOutDate()));
            logger.info("Успешно получено {} клиентов", manager.getAllActiveBookings(SortType.DATE_END).size());
        } catch (Exception e) {
            logger.error("Ошибка при получении клиентов: ", e);
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}