package UI.action.room;

import UI.action.Action;
import Controller.ManagerHotel;
import java.util.Scanner;

public class getRoomDetailsAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public getRoomDetailsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.print("\nВведите номер комнаты: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.println(manager.getRoomDetails(roomNumber));
    }
}