package UI.action.room;

import UI.action.Action;
import Controller.ManagerHotel;
import java.util.Scanner;

public class getLastThreeRoomClientsAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public getLastThreeRoomClientsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.print("\nИстория номера\nВведите номер: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Последние 3 клиента:");
        manager.getLastThreeBookingsForRoom(roomNumber)
                .forEach(b -> System.out.println(b.getClient()));
    }
}