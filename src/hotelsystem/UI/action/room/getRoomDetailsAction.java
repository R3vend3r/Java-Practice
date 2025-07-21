package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import java.util.Scanner;
import java.util.InputMismatchException;

public class getRoomDetailsAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public getRoomDetailsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        try {
            System.out.print("\nВведите номер комнаты: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            String roomDetails = manager.getRoomDetails(roomNumber);
            System.out.println(roomDetails);

        } catch (InputMismatchException e) {
            System.err.println("Ошибка: Номер комнаты должен быть целым числом");
            scanner.nextLine();
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}