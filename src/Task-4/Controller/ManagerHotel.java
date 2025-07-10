package Controller;

import enums.RoomCondition;
import enums.SortType;
import interfaceClass.*;
import java.util.*;
import java.util.stream.Collectors;

import model.*;
import service.*;
public class ManagerHotel {
    private String nameHotel;
    private final RoomService roomService;
    private final AmenityService amenityService;
    private final ClientService clientService;
    private final OrderService orderService;

    public ManagerHotel() {
        IRoomRepository roomRepository = new RoomRepository();
        IAmenityRepository amenityRepository = new AmenityRepository();
        IClientRepository clientRepository = new ClientRepository();
        IOrderRepository orderRepository = new OrderRepository();

        this.roomService = new RoomService(roomRepository);
        this.amenityService = new AmenityService(amenityRepository);
        this.clientService = new ClientService(clientRepository);
        this.orderService = new OrderService(orderRepository);
    }

    public ManagerHotel(String nameHotel) {
        this.nameHotel = Objects.requireNonNull(nameHotel, "Hotel name cannot be null");

        IRoomRepository roomRepository = new RoomRepository();
        IAmenityRepository amenityRepository = new AmenityRepository();
        IClientRepository clientRepository = new ClientRepository();
        IOrderRepository orderRepository = new OrderRepository();

        this.roomService = new RoomService(roomRepository);
        this.amenityService = new AmenityService(amenityRepository);
        this.clientService = new ClientService(clientRepository);
        this.orderService = new OrderService(orderRepository);
    }

    public void settleClient(Client client, Room room, Date checkOutDate) {
        Objects.requireNonNull(client, "Client cannot be null");
        Objects.requireNonNull(room, "Room cannot be null");
        Objects.requireNonNull(checkOutDate, "Check-out date cannot be null");

        if (!roomService.findRoom(room.getNumberRoom())
                .map(Room::isAvailable)
                .orElse(false)) {
            throw new IllegalStateException("Room " + room.getNumberRoom() + " is not available");
        }


        orderService.createRoomBooking(client, room, new Date(), checkOutDate);
        roomService.assignClientToRoom(room.getNumberRoom(), client.getClientId(), checkOutDate);
        roomService.markRoomOccupied(room);
        clientService.assignRoomToClient(client.getClientId(), room.getNumberRoom());
    }

    public void evictClient(int roomNumber) {
        orderService.completeRoomBooking(roomNumber, new Date());
        roomService.clearRoom(roomNumber);
        clientService.removeClientByRoomNumber(roomNumber);
    }

    public Optional<Client> findClientByRoom(int roomNumber) {
        return clientService.findClientByRoomNumber(roomNumber);
    }

    public Optional<Room> findRoom(int roomNumber) {
        return roomService.findRoom(roomNumber);
    }

    public Optional<Client> findClientById(String clientId) {
        return clientService.findClientById(clientId);
    }

    public Optional<Amenity> findAmenityByName(String name) {
        return amenityService.findAmenityByName(name);
    }

    public double calculatingTotalIncome(){
        return orderService.calculatingTotalIncome();
    }

    public void registerClient(Client client) {
        clientService.registerClient(client);
    }

    public void addRoom(Room room) {
        roomService.addRoom(room);
    }

    public void addAmenity(Amenity amenity) {
        amenityService.addAmenity(amenity);
    }

    public void updateRoomStatus(int number, RoomCondition status) {
        roomService.updateRoomStatus(number, status);
    }

    public void updateRoomPrice(int number, double newPrice) {
        roomService.updateRoomPrice(number, newPrice);
    }

    public void updateAmenityPrice(String amenityName, double newPrice) {
        amenityService.updateAmenityPrice(amenityName, newPrice);
    }

    public Map<Integer, Room> getRooms(SortType sortType, boolean onlyAvailable) {
        return onlyAvailable
                ? roomService.getSortedAvailableRooms(sortType)
                : roomService.getSortedRooms(sortType);
    }

    public List<RoomBooking> getAllActiveBookings(SortType sortType) {
        return orderService.getActiveBookingsSorted(sortType);
    }

    public List<RoomBooking> getAllCompletedBookings() {
        return orderService.getCompletedBookings();
    }

    public List<AmenityOrder> getClientAmenitiesSorted(Client client, SortType sortType) {
        return orderService.getAmenityOrdersSorted(sortType).stream()
                .filter(order -> order.getClient().equals(client))
                .collect(Collectors.toList());
    }

    public List<Amenity> getAmenities(SortType sortType) {
        return switch (sortType) {
            case PRICE -> amenityService.getAmenitiesSortedByPrice();
            case ALPHABET -> amenityService.getAmenitiesSortedByName();
            case NONE -> amenityService.getAllAmenities();
            default -> throw new IllegalArgumentException("Unsupported sort type for amenities");
        };
    }

    public void addAmenityToClient(int number, Amenity amenity, Date serviceDate) {
        if (number <= 0) {
            throw new IllegalStateException("Client is not assigned to any room");
        }

        orderService.addAmenityToBooking(number, amenity, serviceDate);
    }

    public List<RoomBooking> getLastThreeBookingsForRoom(int roomNumber) {
        return orderService.getLastThreeBookingsForRoom(roomNumber);
    }

    public Map<Integer, Room> getAvailableRoomsByDate(Date date) {
        return roomService.getAvailableRoomsByDate(date);
    }

    public double calculateRoomPayment(int roomNumber, Date endDate) {
        double payRoom = roomService.calculateStayCost(roomNumber, endDate);
        double payAmenity = orderService.calculateAmenityCost(roomNumber);
        return payRoom + payAmenity;
    }

    public double calculateTotalRevenue() {
        return orderService.calculatingTotalIncome();
    }

    public int getAvailableRoomsCount() {
        return roomService.countAvailableRooms();
    }

    public boolean isRoomAvailable(int number){
        return roomService.isRoomAvailable(number);
    }

    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }


    public int getClientCount(){
        return clientService.getClientCount();
    }

    public String getRoomDetails(int roomNumber) {
        return roomService.getRoomDetails(roomNumber);
    }

    public String getNameHotel() {
        return nameHotel;
    }
}