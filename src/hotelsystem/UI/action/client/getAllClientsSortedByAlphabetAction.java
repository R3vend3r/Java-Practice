package hotelsystem.UI.action.client;

import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.Controller.ManagerHotel;

public class getAllClientsSortedByAlphabetAction implements Action {
    private final ManagerHotel manager;

    public getAllClientsSortedByAlphabetAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\nКлиенты (по алфавиту):");
        manager.getAllActiveBookings(SortType.ALPHABET)
                .forEach(c -> System.out.println(c.getClient().getName() + " " + c.getClient().getSurname()));
    }
}