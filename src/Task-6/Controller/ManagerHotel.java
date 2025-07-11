package Controller;

import csv.*;
import Exception.*;
import enums.RoomCondition;
import enums.SortType;
import interfaceClass.*;
import java.util.*;
import java.util.stream.Collectors;

import model.*;
import service.*;

public class ManagerHotel {
//    private String nameHotel;
private static final ManagerHotel INSTANCE = new ManagerHotel();
    private final RoomService roomService;
    private final AmenityService amenityService;
    private final ClientService clientService;
    private final OrderService orderService;
    private final ICsvService<Room> roomCsvService;
    private final ICsvService<Amenity> amenityCsvService;
    private final ICsvService<Client> clientCsvService;
    private final ICsvService<RoomBooking> roomBookingCsvService;
    private final ICsvService<AmenityOrder> amenityOrderCsvService;


    private ManagerHotel() {
        IRoomRepository roomRepository = new RoomRepository();
        IAmenityRepository amenityRepository = new AmenityRepository();
        IClientRepository clientRepository = new ClientRepository();
        IOrderRepository orderRepository = new OrderRepository();

        this.roomService = new RoomService(roomRepository);
        this.amenityService = new AmenityService(amenityRepository);
        this.clientService = new ClientService(clientRepository);
        this.orderService = new OrderService(orderRepository);
        this.roomCsvService = new RoomCsvService();
        this.amenityCsvService = new AmenityCsvService();
        this.clientCsvService = new ClientCsvService();
        this.roomBookingCsvService = new RoomBookingCsvService();
        this.amenityOrderCsvService = new AmenityOrderCsvService();
    }

    public static ManagerHotel getInstance() {
        return INSTANCE;
    }

//    public ManagerHotel(String nameHotel) {
//        this.nameHotel = Objects.requireNonNull(nameHotel, "Hotel name cannot be null");
//
//        IRoomRepository roomRepository = new RoomRepository();
//        IAmenityRepository amenityRepository = new AmenityRepository();
//        IClientRepository clientRepository = new ClientRepository();
//        IOrderRepository orderRepository = new OrderRepository();
//
//        this.roomService = new RoomService(roomRepository);
//        this.amenityService = new AmenityService(amenityRepository);
//        this.clientService = new ClientService(clientRepository);
//        this.orderService = new OrderService(orderRepository);
//    }

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
        roomService.assignClientToRoom(room.getNumberRoom(), client.getId(), checkOutDate);
        roomService.markRoomOccupied(room);
        clientService.assignRoomToClient(client.getId(), room.getNumberRoom());
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

    public void exportRoomsToCsv(String filePath) throws DataExportException {
        roomCsvService.exportCsv(new ArrayList<>(roomService.getSortedRooms(SortType.NONE).values()), filePath);
    }

    public List<Room> importRoomsFromCsv(String filePath) throws DataImportException {
        List<Room> importedRooms = roomCsvService.importCsv(filePath);
        for (Room room : importedRooms) {
            roomService.addRoom(room);
        }
        return importedRooms;
    }

    public void exportClientsToCsv(String filePath) throws DataExportException {
        clientCsvService.exportCsv(clientService.getAllClients(), filePath);
    }

    public List<Client> importClientsFromCsv(String filePath) throws DataImportException {
        List<Client> importedClients = clientCsvService.importCsv(filePath);
        for (Client client : importedClients) {
            clientService.registerClient(client);
        }
        return importedClients;
    }

    public void exportAmenitiesToCsv(String filePath) throws DataExportException {
        amenityCsvService.exportCsv(amenityService.getAllAmenities(), filePath);
    }

    public List<Amenity> importAmenitiesFromCsv(String filePath) throws DataImportException {
        List<Amenity> importedAmenities = amenityCsvService.importCsv(filePath);

        for (Amenity amenity : importedAmenities) {
            try {
                Optional<Amenity> existingOpt = amenityService.findAmenityByName(amenity.getName());

                if (existingOpt.isPresent()) {
                    Amenity existing = existingOpt.get();
                    existing.setPrice(amenity.getPrice());
                    amenityService.updateAmenity(existing);
                } else {
                    amenityService.addAmenity(amenity);
                }
            } catch (Exception e) {
                throw new DataImportException("Failed to import amenity '" + amenity.getName() + "': " + e.getMessage());
            }
        }
        return importedAmenities;
    }

    public void exportRoomBookingsToCsv(String filePath) throws DataExportException {
        List<RoomBooking> allBookings = new ArrayList<>();
        allBookings.addAll(orderService.getActiveBookingsSorted(SortType.NONE));
        allBookings.addAll(orderService.getCompletedBookings());
        roomBookingCsvService.exportCsv(allBookings, filePath);
    }

    public List<RoomBooking> importRoomBookingsFromCsv(String filePath) throws DataImportException {
        List<RoomBooking> importedBookings = roomBookingCsvService.importCsv(filePath);
        for (RoomBooking booking : importedBookings) {
            Client client = booking.getClient();
            if (clientService.findClientById(client.getId()).isEmpty()) {
                clientService.registerClient(client);
            }

            Room room = booking.getRoom();
            if (roomService.findRoom(room.getNumberRoom()).isEmpty()) {
                roomService.addRoom(room);
            }

            orderService.createRoomBooking(client, room, booking.getCreationDate(), booking.getCheckOutDate());
        }
        return importedBookings;
    }

    public void exportAmenityOrdersToCsv(String filePath) throws DataExportException {
        List<AmenityOrder> amenityOrderList = new ArrayList<>(orderService.getAmenityOrdersSorted(SortType.NONE));
        amenityOrderCsvService.exportCsv(amenityOrderList, filePath);
    }

    public List<AmenityOrder> importAmenityOrdersFromCsv(String filePath) throws DataImportException {
        List<AmenityOrder> importedOrders = amenityOrderCsvService.importCsv(filePath);
        for (AmenityOrder order : importedOrders) {
            Client client = order.getClient();
            if (clientService.findClientById(client.getId()).isEmpty()) {
                clientService.registerClient(client);
            }

            Amenity amenity = order.getAmenity();
            if (amenityService.findAmenityByName(amenity.getName()).isEmpty()) {
                amenityService.addAmenity(amenity);
            }

            orderService.addAmenityToBooking(
                    client.getRoomNumber(),
                    amenity,
                    order.getServiceDate()
            );
        }
        return importedOrders;
    }

//    public String getNameHotel() {
//        return nameHotel;
//    }
}