package hotelsystem.UI.action.client;

import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class findClientByIdAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public findClientByIdAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() throws IllegalArgumentException {
        try {
            System.out.println("\n=== Поиск клиента ===");
            System.out.print("Введите ID клиента: ");
            String id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                throw new IllegalArgumentException("ID клиента не может быть пустым");
            }
            manager.findClientById(id)
                    .ifPresentOrElse(
                            client -> System.out.println("\nНайден клиент:\n" + client),
                            () -> { throw new NoSuchElementException("Клиент с ID '" + id + "' не найден"); }
                    );
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (NoSuchElementException e) {
            System.err.println("Ошибка поиска: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Системная ошибка: " + e.getMessage());
        }
    }
}