import java.util.ArrayList;
import java.util.List;

public class ManagerHotel {
    private String nameHotel;
    private List<Room> roomsList;
    private List<Service> servicesList;
    private List<Client> clientsList;

    public ManagerHotel(String nameHotel) {
        this.nameHotel = nameHotel;
        roomsList = new ArrayList<>();
        servicesList = new ArrayList<>();
        clientsList = new ArrayList<>();
    }

    public void createRoom(Room room){
        if (roomsList == null || room == null){
            return;
        }
        for(Room r: roomsList){
            if(r.getNumberRoom() == room.getNumberRoom()){
                System.out.println("Комната с таким номером уже существует");
                return;
            }
        }
        roomsList.add(room);
        System.out.println("Комната " + room.getNumberRoom() + " успешно добавлена!");
    }

    public void deleteRoom(Room delRoom){
        if (roomsList == null || delRoom == null){
            System.out.println("Список комнат пустой!");
            return;
        }
        for(Room r: roomsList){
            if(r.getNumberRoom() == delRoom.getNumberRoom()){
                roomsList.remove(delRoom);
                System.out.println("Комната " + delRoom.getNumberRoom() + " успешно удалена!");
                return;
            }
         }
        System.out.println("Такой комнаты нету!");
    }

    public void settle(Client client, Room room){
        if (roomsList == null || room == null || client == null){
            System.out.println("Данные неверны!!!");
            return;
        }
        for(Room roomHotel: roomsList){
            if(roomHotel.getNumberRoom() == room.getNumberRoom()){
                if (roomHotel.isEmpty() && roomHotel.getRoomCondition() == RoomCondition.READY){
                    roomHotel.signUpTenant(client);
                    clientsList.add(client);
                    client.setRoomLive(roomHotel);
                    System.out.println("Клиент: " + client.getName() + " успешно заселен в комнату " + roomHotel.getNumberRoom());
                    return;
                }
                else if(roomHotel.getRoomCondition() == RoomCondition.ON_REPAIR){
                    System.out.println("Комната на ремонте");
                    return;
                }
                else if (roomHotel.getRoomCondition() == RoomCondition.CLEANING_REQUIRED){
                    System.out.println("Комната убирается");
                    return;
                }
                System.out.println("Комната занята!!!");
                return;
            }
        }
    }

    public void evictClient(Client client){
        if (client == null || clientsList ==null){
            System.out.println("Клиент задан некорректно!");
            return;
        }
        Room room = client.getRoomLive();
        if (room == null){
            System.out.println("У клиента нету комнаты!");
            return;

        }
        room.clearRoom();
        clientsList.remove(client);
        System.out.println("Клиент успешно выселен!");
    }

    public void addService(Service service){
        if (servicesList == null || service == null){
            return;
        }
        for(Service serv: servicesList){
            if((serv.getNameService()).equals(service.getNameService())){
                System.out.println("Такая услуга уже существует");
                return;
            }
        }
        servicesList.add(service);
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

    public void changeStatusRoom(Room room, RoomCondition status){
        if (room == null){
            System.out.println("Комната не может быть null");
        }
        else {
            room.setRoomCondition(status);
        }

    }

    public List<Room> getRoomsList() {
        return roomsList;
    }

    public String getNameHotel() {
        return nameHotel;
    }

    public List<Service> getServicesList() {
        return servicesList;
    }

    public List<Client> getClientsList() {
        return clientsList;
    }
}
