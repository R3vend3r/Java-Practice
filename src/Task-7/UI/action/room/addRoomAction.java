package UI.action.room;

import UI.action.Action;
import enums.RoomType;
import Controller.ManagerHotel;
import model.Room;
import java.util.Scanner;

public class addRoomAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public addRoomAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        try {
            System.out.println("\nДобавление номера:");
            System.out.print("Номер комнаты: ");
            int number = scanner.nextInt();

            System.out.println("Тип комнаты (1-5):");
            for (RoomType type : RoomType.values()) {
                System.out.println((type.ordinal()+1) + ". " + type.name());
            }
            System.out.print("Выберите тип: ");
            RoomType roomType = RoomType.values()[scanner.nextInt()-1];

            System.out.print("Цена за ночь: ");
            double price = scanner.nextDouble();

            System.out.print("Вместимость: ");
            int capacity = scanner.nextInt();

            manager.addRoom(new Room(number, roomType, price, capacity));
            System.out.println("Номер добавлен");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            scanner.nextLine();
        }
    }
}