package hotelsystem.UI.action.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.model.Client;
import java.util.List;
import java.util.Scanner;

public class getLastThreeRoomClientsAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(getLastThreeRoomClientsAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);

    public getLastThreeRoomClientsAction(ManagerHotel manager) {
        this.manager = manager;
    }

    @Override
    public void execute() {
        try {
            System.out.print("\nПоследние постояльцы номера\nВведите номер: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            List<Client> history = manager.getRoomHistory(roomNumber);
            int limit = Math.min(history.size(), 3);
            logger.info("Запрос последних {} постояльцев номера {}", limit, roomNumber);

            if (limit == 0) {
                System.out.println("История для комнаты " + roomNumber + " пуста");
            } else {
                System.out.println("Последние " + limit + " постояльца комнаты " + roomNumber + ":");
                history.subList(0, limit).forEach(client ->
                        System.out.println("- " + client.getName() + " " + client.getSurname())
                );
            }
        } catch (Exception e) {
            logger.error("Ошибка при получении истории номера: {}", e.getMessage());
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}