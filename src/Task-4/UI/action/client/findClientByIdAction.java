package UI.action.client;

import UI.action.Action;
import Controller.ManagerHotel;
import java.util.Scanner;

public class findClientByIdAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public findClientByIdAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        System.out.print("\nПоиск клиента\nID клиента: ");
        String id = scanner.nextLine();

        manager.findClientById(id).ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Клиент не найден")
        );
    }
}