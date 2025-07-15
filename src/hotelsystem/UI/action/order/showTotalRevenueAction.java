package hotelsystem.UI.action.order;

import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;

public class showTotalRevenueAction implements Action {
    private final ManagerHotel manager;

    public showTotalRevenueAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.printf("\nОбщий доход: %.2f руб.%n",
                manager.calculateTotalRevenue());
    }
}