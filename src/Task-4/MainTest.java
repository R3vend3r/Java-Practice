import model.*;
import enums.*;
import service.*;
import java.util.*;

public class MainTest {
    public static void main(String[] args) {
        // Инициализация
        ManagerHotel hotel = new ManagerHotel("Grand Hotel");
        ClientService clientService = new ClientService();
        RoomService roomService = new RoomService();
        AmenityService amenityService = new AmenityService();
        OrderService orderService = new OrderService();

        // Создание тестовых данных
        Room room101 = new Room(101, 3000.0, 3);
        Room room102 = new Room(102, 5000.0, 4);
        Room room103 = new Room(103, 10000.0, 5);

        Client client1 = new Client("CL001", "Иванов Иван");
        Client client2 = new Client("CL002", "Петров Петр");

        Amenity breakfast = new Amenity( "Завтрак", 500.0);
        Amenity cleaning = new Amenity("Уборка", 300.0);

        // Тестирование основных функций
        System.out.println("\n=== Тестирование добавления комнат ===");
        hotel.addRoom(room101);
        hotel.addRoom(room102);
        hotel.addRoom(room103);
        System.out.println("Все комнаты: " + hotel.getRooms(SortType.PRICE, false).keySet());

        System.out.println("\n=== Тестирование добавления услуг ===");
        hotel.addAmenity(breakfast);
        hotel.addAmenity(cleaning);
        System.out.println("Все услуги: " + hotel.getAmenities(SortType.ALPHABET));

        // Тестирование заселения
        System.out.println("\n=== Тестирование заселения ===");
        Date checkOut1 = new Date(System.currentTimeMillis() + 86400000 * 3); // +3 дня
        Date checkOut2 = new Date(System.currentTimeMillis() + 86400000 * 5); // +5 дня

        System.out.println("Заселяем клиента 1 в номер 101:");
        hotel.settleClient(client1, room101, checkOut1, room101.getPriceForDay());

        System.out.println("Заселяем клиента 2 в номер 102:");
        hotel.settleClient(client2, room102, checkOut2, room102.getPriceForDay());

        System.out.println("Текущие заселения: " + hotel.getActiveClientsCount());

        // Тестирование услуг
        System.out.println("\n=== Тестирование добавления услуг клиенту ===");
        System.out.println("Добавляем завтрак клиенту 1:");
        hotel.addClientAmenity(client2, breakfast, checkOut2);
        hotel.addClientAmenity(client2, cleaning, checkOut1);
        hotel.addClientAmenity(client1, cleaning, checkOut1);

        hotel.getClientAmenities(client2, SortType.PRICE).forEach(o ->
                System.out.println(o.getService().getName() + ": " + o.getService().getPrice()));

        // Тестирование сортировок
        System.out.println("\n=== Тестирование сортировок ===");
        System.out.println("1. Номера по цене:");
        hotel.getRooms(SortType.PRICE, false).forEach((k,v) ->
                System.out.println("Номер " + v.getNumberRoom() + ": " + v.getPriceForDay() + " руб."));

        System.out.println("\n2. Свободные номера по звездам:");
        hotel.getRooms(SortType.STARS, true).forEach((k,v) ->
                System.out.println("Номер " + v.getNumberRoom() + ": " + v.getStars() + " звезд(ы)"));

        System.out.println("\nВсе клиенты по алфавиту:");
        hotel.getAllClientsSorted(SortType.ALPHABET).forEach(c ->
                System.out.println(c.getClient().getName()));

        // Тестирование специальных запросов
        System.out.println("\n=== Тестирование специальных запросов ===");
        System.out.println("1. 3 последних клиента номера 101:");
        hotel.getLastThreeRoomClients(101).forEach(o ->
                System.out.println(o.getClient().getName() + " - выезд: " + o.getDateOut()));

        System.out.println("\n2. Услуги клиента " + client1.getName() + " по цене:");

        hotel.getClientAmenities(client1, SortType.PRICE).forEach(o ->
                System.out.println("- " + o.getService().getName() + ": " + o.getService().getPrice() + " руб."));

        System.out.println("\n3. Детали номера 102:");
        System.out.println(hotel.getRoomDetails(102));

        // Тестирование статистики
        System.out.println("\n=== Тестирование статистики ===");
        System.out.println("1. Свободных номеров: " + hotel.getAvailableRoomsCount());
        System.out.println("2. Всего клиентов: " + hotel.getTotalClientsCount());
        System.out.println("3. Активных клиентов: " + hotel.getActiveClientsCount());

        // Тестирование изменения данных
        System.out.println("\n=== Тестирование изменений ===");
        System.out.println("1. Изменяем статус номера 103 на ремонт:");
        hotel.changeRoomStatus(room103, RoomCondition.ON_REPAIR);
        System.out.println("Статус: " + room103.getRoomCondition());

        System.out.println("\n2. Обновляем цену номера 101:");
        hotel.updateRoomPrice(room101, 3500.0);
        System.out.println("Новая цена: " + room101.getPriceForDay() + " руб.");

        System.out.println("\n3. Обновляем цену завтрака:");
        hotel.updateAmenityPrice(breakfast, 600.0);
        System.out.println("Новая цена: " + breakfast.getPrice() + " руб.");

        // Тестирование выселения
        System.out.println("\n=== Тестирование выселения ===");
        System.out.println("Выселяем клиента из номера 101:");
        hotel.evictClient(101);
        System.out.println("Свободных номеров теперь: " + hotel.getAvailableRoomsCount());

        // Проверка ошибок
        System.out.println("\n=== Тестирование обработки ошибок ===");
        System.out.println("1. Попытка заселения в занятый номер 102:");
        hotel.settleClient(new Client("CL003", "Сидоров Сидор"), room102, checkOut1, 5000.0);

        Amenity nonExisting = new Amenity("Несуществующая", 0);

        System.out.println("Попытка изменения несуществующей услуги:");
        hotel.updateAmenityPrice(nonExisting, 100.0);

        // Проверка что услуга не добавилась
         hotel.getAmenities(SortType.NONE);
    }
}