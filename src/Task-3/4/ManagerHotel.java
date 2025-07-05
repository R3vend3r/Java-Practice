import java.util.ArrayList;
import java.util.List;

public class ManagerHotel {
    private String nameHotel;
    private List<Room> rooms;
    private List<Service> services;
    private List<Client> clients;

    public ManagerHotel(String nameHotel) {
        this.nameHotel = nameHotel;
        rooms = new ArrayList<>();
        services = new ArrayList<>();
        clients = new ArrayList<>();
    }

    public void createRoom(Room room){
        if (rooms == null || room == null){
            return;
        }
        for(Room r: rooms){
            if(r.getNumberRoom() == room.getNumberRoom()){
                System.out.println("Комната с таким номером уже существует");
                return;
            }
        }
        rooms.add(room);
        System.out.println("Комната " + room.getNumberRoom() + " успешно добавлена!");
    }

    public boolean deleteRoom(Room delRoom){
        if (rooms == null || delRoom == null){
            System.out.println("Список комнат пустой!");
            return false;
        }
        for(Room r: rooms){
            if(r.getNumberRoom() == delRoom.getNumberRoom()){
                rooms.remove(delRoom);
                System.out.println("Комната " + delRoom.getNumberRoom() + " успешно удалена!");
                return true;
            }
         }
        System.out.println("Такой комнаты нету!");
        return false;
    }

    public void settle(Client client, Room room){
        if (rooms == null || room == null || client == null){
            System.out.println("Данные неверны!!!");
            return;
        }
        for(Room roomHotel: rooms){
            if(roomHotel.getNumberRoom() == room.getNumberRoom()){
                if (roomHotel.isEmpty() && roomHotel.getStatus() == StatusRoom.READY){
                    roomHotel.signUpTenant(client);
                    clients.add(client);
                    client.setRoomLive(roomHotel);
                    System.out.println("Клиент: " + client.getName() + " успешно заселен в комнату " + roomHotel.getNumberRoom());
                    return;
                }
                else if(roomHotel.getStatus() == StatusRoom.ON_REPAIR){
                    System.out.println("Комната на ремонте");
                    return;
                }
                else if (roomHotel.getStatus() == StatusRoom.CLEANING_REQUIRED){
                    System.out.println("Комната убирается");
                    return;
                }
                System.out.println("Комната занята!!!");
                return;
            }
        }
    }

    public void evictClient(Client client){
        if (client == null || clients ==null){
            System.out.println("Клиент задан некорректно!");
            return;
        }
        Room room = client.getRoomLive();
        if (room == null){
            System.out.println("У клиента нету комнаты!");
            return;

        }
        room.clearRoom();
        clients.remove(client);
        System.out.println("Клиент успешно выселен!");
    }

    public void addService(Service service){
        if (services == null || service == null){
            return;
        }
        for(Service serv: services){
            if((serv.getNameService()).equals(service.getNameService())){
                System.out.println("Такая услуга уже существует");
                return;
            }
        }
        services.add(service);
        System.out.println("Услуга " + service.getNameService() + " успешно добавлена!");
    }

    public void changePriceRoom(Room room, double newPrice){
        assert room != null;
        room.setPriceForDay(newPrice);
    }

    public void changePriceService(Service service, double newPrice){
        assert service != null;
        service.setPrice(newPrice);
    }

    public void changeStatusRoom(Room room, StatusRoom status){
        if (room == null){
            System.out.println("Комната не может быть null");
        }
        else {
            room.setStatus(status);
        }

    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setNameHotel(String nameHotel) {
        this.nameHotel = nameHotel;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public List<Service> getServices() {
        return services;
    }

    public List<Client> getClients() {
        return clients;
    }
}
