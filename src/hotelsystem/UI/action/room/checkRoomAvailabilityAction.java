package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import java.util.Scanner;

public class checkRoomAvailabilityAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public checkRoomAvailabilityAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.print("\nПроверка номера\nВведите номер: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        System.out.println(manager.isRoomAvailable(number) ? "Свободен" : "Занят");
    }
}