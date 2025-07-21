package hotelsystem.UI.action.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllClientsSortedByAlphabetAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getAllClientsSortedByAlphabetAction.class);
    private final ManagerHotel manager;

    public getAllClientsSortedByAlphabetAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало получения клиентов, отсортированных по алфавиту");
        try {
            System.out.println("\nКлиенты (по алфавиту):");
            manager.getAllActiveBookings(SortType.ALPHABET)
                    .forEach(c -> System.out.println(c.getClient().getName() + " " + c.getClient().getSurname()));
            logger.info("Успешно получено {} клиентов", manager.getAllActiveBookings(SortType.ALPHABET).size());
        } catch (Exception e) {
            logger.error("Ошибка при получении клиентов: ", e);
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}