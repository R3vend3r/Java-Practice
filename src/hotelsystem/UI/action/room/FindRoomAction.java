package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.model.Room;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Optional;

public class FindRoomAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(FindRoomAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public FindRoomAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        try {
            System.out.print("\nВведите номер комнаты: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            Optional<Room> roomOpt = manager.findRoom(roomNumber);

            if (roomOpt.isPresent()) {
                Room room = roomOpt.get();
                logger.info("Найден номер: {}", room);
                System.out.println("\n=== Информация о комнате ===");
                System.out.printf("Номер: %d\n", room.getNumberRoom());
                System.out.printf("Тип: %s\n", room.getType());
                System.out.printf("Статус: %s\n", room.getRoomCondition());
                System.out.printf("Цена за день: %.2f\n", room.getPriceForDay());
                System.out.printf("Вместимость: %d\n", room.getCapacity());
                System.out.printf("Звезды: %d\n", room.getStars());
                System.out.printf("Доступна: %s\n", room.isAvailable() ? "Да" : "Нет");

                if (room.getClientId() != null) {
                    System.out.printf("ID клиента: %s\n", room.getClientId());
                }
                if (room.getAvailableDate() != null) {
                    System.out.printf("Дата освобождения: %s\n", room.getAvailableDate());
                }
            } else {
                logger.warn("Комната с номером {} не найдена", roomNumber);
                System.out.println("Комната с номером " + roomNumber + " не найдена");
            }

        } catch (InputMismatchException e) {
            logger.error("Ошибка ввода: номер комнаты должен быть целым числом");
            System.err.println("Ошибка: Номер комнаты должен быть целым числом");
            scanner.nextLine();
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при поиске номера: {}", e.getMessage());
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        }
    }
}