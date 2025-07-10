package UI.action.client;

import UI.action.Action;
import Controller.ManagerHotel;

public class getTotalServicedClientAction implements Action {
    private final ManagerHotel manager;

    public getTotalServicedClientAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.printf("\nОбслужено клиентов: %d%n",
                manager.getClientCount());
    }
}