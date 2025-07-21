package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.model.Room;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class getAvailableRoomsByDateAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
    private final SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    public getAvailableRoomsByDateAction(ManagerHotel manager) {
        this.manager = manager;
        dateFormat.setLenient(false);
    }

    @Override
    public void execute() {
        try {
            System.out.print("\nПроверка доступности\nДата (дд.мм.гг): ");
            String dateStr = scanner.nextLine();

            if (dateStr.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
                dateStr = dateStr.substring(0, 6) + "20" + dateStr.substring(6);
            }

            Date date = dateFormat.parse(dateStr);

            if (date.before(new Date())) {
                System.out.println("Ошибка: дата должна быть в будущем");
                return;
            }

            System.out.println("\nДоступные номера:");
            List<Room> availableRooms = manager.getAvailableRoomsByDate(date);

            if (availableRooms.isEmpty()) {
                System.out.println("Нет доступных номеров на эту дату");
                return;
            }

            availableRooms.forEach(room -> {
                String status = room.isAvailable()
                        ? "Свободен сейчас"
                        : "Освободится " + outputDateFormat.format(room.getAvailableDate());
                System.out.printf("%d - %s (%s)%n",
                        room.getNumberRoom(),
                        room.getType(),
                        status);
            });

        } catch (Exception e) {
            System.out.println("Ошибка: неверный формат даты. Используйте дд.мм.гг");
        }
    }
}