package UI.action.order;

import UI.action.Action;
import enums.SortType;
import model.Client;
import Controller.ManagerHotel;
import model.Room;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class settleClientAction implements Action {
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

    public settleClientAction(ManagerHotel manager) {
        this.manager = manager;
        dateFormat.setLenient(false);
    }

    @Override
    public void execute() {
        try {
            System.out.println("\n=== Заселение клиента ===");

            // Регистрация клиента
            System.out.print("Имя: ");
            String name = scanner.nextLine().trim();
            System.out.print("Фамилия: ");
            String surname = scanner.nextLine().trim();

            if(name.isEmpty() || surname.isEmpty()) {
                System.out.println("Ошибка: имя и фамилия не могут быть пустыми");
                return;
            }

            Client client = new Client(name, surname);
            manager.registerClient(client);

            // Получаем свободные номера
            List<Room> availableRooms = manager.getRooms(SortType.NONE, true).values()
                    .stream()
                    .filter(Room::isAvailable)
                    .toList();

            if(availableRooms.isEmpty()) {
                System.out.println("\nНет свободных номеров для заселения!");
                return;
            }

            // Вывод свободных номеров
            System.out.println("\nДоступные номера:");
            availableRooms.forEach(r -> System.out.printf("%d - %s (%.2f руб.)%n",
                    r.getNumberRoom(), r.getType(), r.getPriceForDay()));

            // Выбор номера
            System.out.print("\nНомер для заселения: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            Room room = availableRooms.stream()
                    .filter(r -> r.getNumberRoom() == roomNumber)
                    .findFirst()
                    .orElse(null);

            if(room == null) {
                System.out.println("Ошибка: указан несуществующий или занятый номер");
                return;
            }

            // Дата выезда
            System.out.print("Дата выезда (дд.мм.гг): ");
            String dateStr = scanner.nextLine();
            // Добавляем "20" если введен короткий год
            if (dateStr.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
                dateStr = dateStr.substring(0, 6) + "20" + dateStr.substring(6);
            }
            Date checkOut = dateFormat.parse(dateStr);

            // Проверка что дата выезда в будущем
            if(checkOut.before(new Date())) {
                System.out.println("Ошибка: дата выезда должна быть в будущем");
                return;
            }

            // Подтверждение
            System.out.printf("%nПодтвердите заселение:%n%s %s (ID: %s) в номер %d до %s%n",
                    client.getName(), client.getSurname(), client.getClientId(),
                    room.getNumberRoom(), new SimpleDateFormat("dd.MM.yyyy").format(checkOut));
            System.out.print("Подтвердить (да/нет)? ");
            String confirmation = scanner.nextLine();

            if(confirmation.equalsIgnoreCase("да")) {
                manager.settleClient(client, room, checkOut);
                System.out.println("Клиент успешно заселен в номер " + room.getNumberRoom());
            } else {
                System.out.println("Заселение отменено");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
            if (e.getMessage().contains("Unparseable date")) {
                System.out.println("Используйте формат дд.мм.гг (например: 15.07.25)");
            }
        }
    }
}