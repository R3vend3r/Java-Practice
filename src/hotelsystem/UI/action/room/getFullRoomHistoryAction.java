package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.model.Client;
import java.util.List;
import java.util.Scanner;

public class getFullRoomHistoryAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getFullRoomHistoryAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public getFullRoomHistoryAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        try {
            System.out.print("\nПолная история номера\nВведите номер комнаты: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            List<Client> history = manager.getRoomHistory(roomNumber);
            logger.info("Запрос полной истории номера {}, найдено {} записей", roomNumber, history.size());

            if (history.isEmpty()) {
                System.out.println("История для комнаты " + roomNumber + " пуста");
            } else {
                System.out.println("Полная история комнаты " + roomNumber + ":");
                history.forEach(client ->
                        System.out.println("- " + client.getName() +" " + client.getSurname() + " (ID: " + client.getId() + ")")
                );
            }
        } catch (Exception e) {
            logger.error("Ошибка при получении истории номера: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}