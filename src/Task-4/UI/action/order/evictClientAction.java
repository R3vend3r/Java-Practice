package UI.action.order;

import UI.action.Action;
import Controller.ManagerHotel;
import java.util.Scanner;

public class evictClientAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public evictClientAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        try {
            System.out.println("\n=== Выселение клиента ===");
            System.out.print("Номер комнаты: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            manager.findClientByRoom(roomNumber).ifPresentOrElse(
                    client -> {
                        System.out.println("Клиент: " + client);
                        System.out.print("Выселить (да/нет)? ");
                        if(scanner.nextLine().equalsIgnoreCase("да")) {
                            manager.evictClient(roomNumber);
                            System.out.println("Клиент выселен");
                        }
                    },
                    () -> System.out.println("Номер свободен или не существует")
            );
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}