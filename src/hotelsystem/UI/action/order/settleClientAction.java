package hotelsystem.UI.action.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import hotelsystem.UI.action.Action;
import hotelsystem.enums.SortType;
import hotelsystem.model.Client;
import hotelsystem.Controller.ManagerHotel;
import hotelsystem.model.Room;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class settleClientAction implements Action {
    private static final Logger logger = LoggerFactory.getLogger(settleClientAction.class);
    private final ManagerHotel manager;
    private final Scanner scanner = new Scanner(System.in);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

    public settleClientAction(ManagerHotel manager) {
        this.manager = manager;
        dateFormat.setLenient(false);
    }

    @Override
    public void execute() {
        logger.info("Начало процесса заселения клиента");
        try {
            System.out.println("\n=== Заселение клиента ===");

            System.out.print("Имя: ");
            String name = scanner.nextLine().trim();
            System.out.print("Фамилия: ");
            String surname = scanner.nextLine().trim();

            if(name.isEmpty() || surname.isEmpty()) {
                String errorMsg = "Имя и фамилия не могут быть пустыми";
                logger.warn(errorMsg);
                System.out.println("Ошибка: " + errorMsg);
                return;
            }

            Client client = new Client(name, surname);
            manager.registerClient(client);
            logger.info("Зарегистрирован новый клиент: {} {}", name, surname);

            List<Room> availableRooms = manager.getRooms(SortType.NONE, true)
                    .stream()
                    .filter(Room::isAvailable)
                    .toList();

            if(availableRooms.isEmpty()) {
                logger.warn("Нет свободных номеров для заселения");
                System.out.println("\nНет свободных номеров для заселения!");
                return;
            }

            System.out.println("\nДоступные номера:");
            availableRooms.forEach(r -> System.out.printf("%d - %s (%.2f руб.)%n",
                    r.getNumberRoom(), r.getType(), r.getPriceForDay()));
            logger.debug("Доступно {} номеров", availableRooms.size());

            System.out.print("\nНомер для заселения: ");
            int roomNumber = scanner.nextInt();
            scanner.nextLine();

            Room room = availableRooms.stream()
                    .filter(r -> r.getNumberRoom() == roomNumber)
                    .findFirst()
                    .orElse(null);

            if(room == null) {
                String errorMsg = "Указан несуществующий или занятый номер";
                logger.warn(errorMsg);
                System.out.println("Ошибка: " + errorMsg);
                return;
            }

            System.out.print("Дата выезда (дд.мм.гг): ");
            String dateStr = scanner.nextLine();
            if (dateStr.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
                dateStr = dateStr.substring(0, 6) + "20" + dateStr.substring(6);
            }
            Date checkOut = dateFormat.parse(dateStr);

            if(checkOut.before(new Date())) {
                String errorMsg = "Дата выезда должна быть в будущем";
                logger.warn(errorMsg);
                System.out.println("Ошибка: " + errorMsg);
                return;
            }

            String confirmationInfo = String.format("%s %s (ID: %s) в номер %d до %s",
                    client.getName(), client.getSurname(), client.getId(),
                    room.getNumberRoom(), new SimpleDateFormat("dd.MM.yyyy").format(checkOut));
            System.out.printf("%nПодтвердите заселение:%n%s%n", confirmationInfo);
            System.out.print("Подтвердить (да/нет)? ");
            String confirmation = scanner.nextLine();

            if(confirmation.equalsIgnoreCase("да")) {
                manager.settleClient(client, room, checkOut);
                logger.info("Клиент успешно заселен: {}", confirmationInfo);
                System.out.println("Клиент успешно заселен в номер " + room.getNumberRoom());
            } else {
                logger.info("Заселение клиента отменено пользователем");
                System.out.println("Заселение отменено");
            }
        } catch (IllegalArgumentException e) {
            logger.error("Ошибка ввода данных", e);
            System.err.println("Ошибка ввода: " + e.getMessage());
        } catch (NoSuchElementException e) {
            logger.error("Ошибка выбора номера", e);
            System.err.println("Ошибка выбора: " + e.getMessage());
        } catch (ParseException e) {
            logger.error("Ошибка формата даты", e);
            System.err.println("Ошибка формата даты: используйте дд.мм.гг (например: 15.07.25)");
        } catch (IllegalStateException e) {
            logger.error("Ошибка операции заселения", e);
            System.err.println("Ошибка операции: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Неожиданная ошибка при заселении", e);
            System.err.println("Неожиданная ошибка: " + e.getMessage());
        } finally {
            logger.info("Завершение процесса заселения");
            scanner.nextLine();
        }
    }
}