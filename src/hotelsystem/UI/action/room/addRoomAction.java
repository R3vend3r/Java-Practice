package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.RoomType;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.model.Room;
import java.util.Scanner;

public class addRoomAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(addRoomAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public addRoomAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        logger.info("Начало добавления нового номера");
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

            Room room = new Room(number, roomType, price, capacity);
            manager.addRoom(room);
            logger.info("Добавлен новый номер: {}", room);
            System.out.println("Номер добавлен");
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("Неверный тип комнаты");
            System.out.println("Ошибка: неверный тип комнаты");
        } catch (Exception e) {
            logger.error("Ошибка при добавлении номера: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.nextLine();
        }
    }
}