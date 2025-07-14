package UI.action.client;

import UI.action.Action;
import enums.SortType;
import Controller.ManagerHotel;

public class getAllClientsSortedByDateEndAction implements Action {
    private final ManagerHotel manager;

    public getAllClientsSortedByDateEndAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nКлиенты (по дате выезда):");
        manager.getAllActiveBookings(SortType.DATE_END)
                .forEach(c -> System.out.printf("%s %s (выезд: %s)%n",
                        c.getClient().getName(), c.getClient().getSurname(), c.getCheckOutDate()));
    }
}