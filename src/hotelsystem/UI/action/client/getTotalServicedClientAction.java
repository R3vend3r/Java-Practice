package hotelsystem.UI.action.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;

public class getTotalServicedClientAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getTotalServicedClientAction.class);
    private final ManagerHotel manager;

    public getTotalServicedClientAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос общего количества обслуженных клиентов");
        try {
            int count = manager.getClientCount();
            System.out.printf("\nОбслужено клиентов: %d%n", count);
            logger.info("Общее количество обслуженных клиентов: {}", count);
        } catch (Exception e) {
            logger.error("Ошибка при получении количества клиентов: ", e);
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}