import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        ManagerHotel hotel = new ManagerHotel("Paratel Aquapark");

        Room room1 = new Room(500.0);
        Room room2 = new Room(750.0);
        Room room3 = new Room(1000.0);

        hotel.createRoom(room1);
        hotel.createRoom(room2);
        hotel.createRoom(room3);

        Client client1 = new Client("Иванов", "Иван");
        Client client2 = new Client("Петров", "Петр");

        hotel.settle(client1, room1);
        hotel.settle(client2, room2);

        System.out.println("Пробуем заселить в занятую комнату:");
        hotel.settle(new Client("Сидоров", "Сидор"), room1);

        Service service1 = new Service(50.0, "Уборка номера");
        hotel.addService(service1);

        hotel.changePriceRoom(room1, 600.0);
        hotel.changePriceService(service1, 60.0);

        hotel.changeStatusRoom(room3, RoomCondition.ON_REPAIR);
        System.out.println();

        System.out.println("Пробуем заселить в комнату на ремонте:");
        hotel.settle(new Client("Смирнов", "Дмитрий"), room3);

        hotel.evictClient(client1);

        System.out.println("Пробуем заселить в комнату после выселения:");
        hotel.settle(new Client("Кузнецов", "Алексей"), room1);
        System.out.println();

        System.out.println("Пробуем выселить несуществующего клиента:");
        hotel.evictClient(new Client("Федоров", "Олег"));
        System.out.println();

        System.out.println("Пробуем добавить существующую услугу:");
        hotel.addService(new Service(70.0, "Уборка номера"));
        System.out.println();

        System.out.println("Список комнат:");
        List<Room> rooms = hotel.getRoomsList();
        for (Room room : rooms) {
            System.out.println(room);
        }
    }
}
