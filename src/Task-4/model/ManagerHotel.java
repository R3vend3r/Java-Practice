package model;

import enums.RoomCondition;
import enums.SortType;
import service.*;
import java.util.*;
import java.util.stream.Collectors;

public class ManagerHotel {
    private final String nameHotel;
    private final RoomService roomService;
    private final AmenityService amenityService;
    private final ClientService clientService;
    private final OrderService orderService;

    public ManagerHotel(String nameHotel) {
        this.nameHotel = nameHotel;
        this.roomService = new RoomService();
        this.amenityService = new AmenityService();
        this.clientService = new ClientService();
        this.orderService = new OrderService();
    }

    // === Основные операции ===
    public void settleClient(Client client, Room room, Date checkOutDate, double price) {
        if (!roomService.isRoomAvailable(room.getNumberRoom())) {
            System.out.println("Номер " + room.getNumberRoom() + " недоступен для заселения");
            return;
        }

        if (!clientService.checkClient(client)) {
            clientService.registerClient(client);
        }

        orderService.createRoomOrder(client, room, new Date(), checkOutDate);
        roomService.markRoomOccupied(room);
        System.out.println("Клиент " + client.getName() + " заселен в номер " + room.getNumberRoom());
    }

    public void evictClient(int roomNumber) {
        try {
            orderService.completeRoomOrder(roomNumber, new Date());
            roomService.clearRoom(roomNumber);
            System.out.println("Номер " + roomNumber + " освобожден");
        } catch (Exception e) {
            System.out.println("Ошибка при выселении: " + e.getMessage());
        }
    }

    public void addRoom(Room room) {
        roomService.addRoom(room);
    }

    public void changeRoomStatus(Room room, RoomCondition status) {
        roomService.changeRoomCondition(room, status);
    }

    public void updateRoomPrice(Room room, double newPrice) {
        roomService.changeRoomPrice(room, newPrice);
    }

    public void addAmenity(Amenity amenity) {
        amenityService.addAmenity(amenity);
    }

    public void addClientAmenity(Client client, Amenity amenity, Date dateIm){
        orderService.createServiceOrder(client, amenity, dateIm);
    }

    public void updateAmenityPrice(Amenity amenity, double newPrice) {
        amenityService.changeAmenityPrice(amenity, newPrice);
    }

    public Map<Integer, Room> getRooms(SortType sortType, boolean onlyAvailable) {
        return onlyAvailable ?
                roomService.getSortedAvailableRooms(sortType) :
                roomService.getSortedRooms(sortType);
    }

    public List<Order> getAllClientsSorted(SortType sortType) {
        return orderService.getSortedOrders(sortType);
    }

    public List<Order> getClientAmenities(Client client, SortType sortType) {
        return orderService.getSortedAmenities(sortType).stream()
                .filter(o -> o.getClient().equals(client))
                .collect(Collectors.toList());
    }

    public List<Amenity> getAmenities(SortType sortType) {
        return switch (sortType) {
            case PRICE -> amenityService.getAmenitiesSortedByPrice().stream().toList();
            case ALPHABET -> amenityService.getAmenitiesSortedByName().stream().toList();
            case NONE -> amenityService.getAllAmenity().stream().toList();
            default -> throw new IllegalArgumentException("Неподдерживаемый тип сортировки для услуг");
        };
    }

    public List<Order> getLastThreeRoomClients(int roomNumber) {
        return orderService.getLastThreeRoomClients(roomNumber);
    }

    public List<Room> getAvailableRoomsByDate(Date date) {
        return roomService.getAvailableRoomsByDate(date);
    }

    public double calculateRoomPayment(Client client, Room room, Date endDate) {
        return roomService.calculateRoomPayment(client, room, endDate);
    }

    public int getAvailableRoomsCount() {
        return roomService.getNumberAvailableRooms();
    }

    public int getTotalClientsCount() {
        return clientService.getClientCount();
    }

    public int getActiveClientsCount() {
        return orderService.countActiveClients();
    }

    public String getRoomDetails(int roomNumber) {
        return roomService.getRoomDetails(roomNumber);
    }



    public String getNameHotel() {
        return nameHotel;
    }
}