package hotelsystem.UI.action.room;

import hotelsystem.UI.action.Action;
import hotelsystem.Controller.ManagerHotel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class getAvailableRoomsByDateAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

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
            var availableRooms = manager.getAvailableRoomsByDate(date);

            if (availableRooms.isEmpty()) {
                System.out.println("Нет доступных номеров на эту дату");
                return;
            }

            availableRooms.values().forEach(r -> {
                String status = r.isAvailable() ? "Свободен сейчас" :
                        "Освободится " + new SimpleDateFormat("dd.MM.yyyy").format(r.getAvailableDate());
                System.out.printf("%d - %s (%s)%n", r.getNumberRoom(), r.getType(), status);
            });

        } catch (Exception e) {
            System.out.println("Ошибка: неверный формат даты. Используйте дд.мм.гг");
        }
    }
}