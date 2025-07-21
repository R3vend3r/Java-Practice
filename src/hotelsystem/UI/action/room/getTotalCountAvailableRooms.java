package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;

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