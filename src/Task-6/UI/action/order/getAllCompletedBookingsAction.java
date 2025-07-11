package UI.action.order;

import UI.action.Action;
import Controller.ManagerHotel;

public class getAllCompletedBookingsAction implements Action {
    private final ManagerHotel manager;

    public getAllCompletedBookingsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.println("\n=== Завершенные бронирования ===");
        manager.getAllCompletedBookings().forEach(booking ->
                System.out.printf("Номер %d - %s (выезд: %s)%n",
                        booking.getRoom().getNumberRoom(),
                        booking.getClient(),
                        booking.getCheckOutDate())
        );
    }
}