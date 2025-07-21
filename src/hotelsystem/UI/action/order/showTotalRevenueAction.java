package hotelsystem.UI.action.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;

public class showTotalRevenueAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(showTotalRevenueAction.class);
    private final ManagerHotel manager;

    public showTotalRevenueAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Запрос общего дохода");
        try {
            double revenue = manager.calculateTotalRevenue();
            System.out.printf("\nОбщий доход: %.2f руб.%n", revenue);
            logger.info("Общий доход: {} руб.", revenue);
        } catch (Exception e) {
            logger.error("Ошибка при расчете общего дохода", e);
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}