package UI.action.room;

import UI.action.Action;
import Controller.ManagerHotel;

public class getTotalCountAvailableRooms implements Action {
    private final ManagerHotel manager;

    public getTotalCountAvailableRooms(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.printf("\nСвободных номеров: %d%n",
                manager.getAvailableRoomsCount());
    }
}