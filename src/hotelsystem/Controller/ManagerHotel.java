package hotelsystem.Controller;

import hotelsystem.Utils.HotelConfig;
import hotelsystem.Utils.*;
import hotelsystem.csv.*;
import hotelsystem.Exception.*;
import hotelsystem.dependencies.annotation.ConfigProperty;
import hotelsystem.dependencies.annotation.Inject;
import hotelsystem.dependencies.annotation.PostConstruct;
import hotelsystem.dependencies.annotation.Variant;
import hotelsystem.enums.RoomCondition;
import hotelsystem.enums.SortType;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import hotelsystem.interfaceClass.IClearable;
import hotelsystem.model.*;
import hotelsystem.service.*;

public class ManagerHotel {

    @Inject private RoomService roomService;
    @Inject private AmenityService amenityService;
    @Inject private ClientService clientService;
    @Inject private OrderService orderService;

    @Inject @Variant("roomCsvService") private ICsvService<Room> roomCsvService;
    @Inject @Variant("amenityCsvService") private ICsvService<Amenity> amenityCsvService;
    @Inject @Variant("clientCsvService") private ICsvService<Client> clientCsvService;
    @Inject @Variant("roomBookingCsvService") private ICsvService<RoomBooking> roomBookingCsvService;
    @Inject @Variant("amenityOrderCsvService") private ICsvService<AmenityOrder> amenityOrderCsvService;

    @Inject private HotelConfig hotelConfig;
    @ConfigProperty(propertyName = "hotel.room.history.max_entries")
    private int maxHistoryEntries;

    private Map<Integer, Queue<Client>> roomHistory = new HashMap<>();
    private boolean initialized = false;

    @PostConstruct
    public void postConstruct() {
        if (!initialized) {
            System.out.println("Система Отеля создалась!");
            initialized = true;
        }
    }

    public void settleClient(Client client, Room room, Date checkOutDate) {
        validateSettleParameters(client, room, checkOutDate);
        orderService.createRoomBooking(client, room, new Date(), checkOutDate);
        updateRoomAndClientState(room, client, checkOutDate);
        addToRoomHistory(room.getNumberRoom(), client);
    }

    private void validateSettleParameters(Client client, Room room, Date checkOutDate) {
        Objects.requireNonNull(client, "Client cannot be null");
        Objects.requireNonNull(room, "Room cannot be null");
        Objects.requireNonNull(checkOutDate, "Check-out date cannot be null");

        if (!roomService.isRoomAvailable(room.getNumberRoom())) {
            throw new IllegalStateException("Room " + room.getNumberRoom() + " is not available");
        }
    }

    private void updateRoomAndClientState(Room room, Client client, Date checkOutDate) {
        roomService.assignClientToRoom(room.getNumberRoom(), client, checkOutDate);
        roomService.markRoomOccupied(room, client);
        clientService.assignRoomToClient(client.getId(), room.getNumberRoom());
    }

    private void addToRoomHistory(int roomNumber, Client client) {
        roomHistory.computeIfAbsent(roomNumber, k -> new LinkedList<>()).add(client);
        trimHistory(roomNumber);
    }

    private void trimHistory(int roomNumber) {
        Queue<Client> history = roomHistory.get(roomNumber);
        if (history != null && history.size() > maxHistoryEntries) {
            history.poll();
        }
    }

    public List<AmenityOrder> getAllAmenityOrders() {
        return orderService.getAllAmenityOrders();
    }

    public void evictClient(int roomNumber) {
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

    public List<AmenityOrder> getAmenitiesForClient(String clientId) {
        Objects.requireNonNull(clientId, "Client ID cannot be null");
        return orderService.getAmenitiesForClient(clientId);
    }

    public void updateRoomStatus(int number, RoomCondition status) {
        if (hotelConfig.isRoomStatusChangeEnabled()) {
            roomService.updateRoomStatus(number, status);
        } else {
            throw new IllegalStateException("Изменение статуса комнаты запрещено конфигурацией");
        }
    }

    public List<Room> getAvailableRooms() {
        return roomService.getAvailableRooms();
    }

    public void updateRoomPrice(int number, double newPrice) {
        roomService.updateRoomPrice(number, newPrice);
    }

    public void updateRoomInfo(Room room) {
        roomService.updateRoom(room);
    }

    public void updateAmenityPrice(String amenityName, double newPrice) {
        amenityService.updateAmenityPrice(amenityName, newPrice);
    }

    public List<Room> getRooms(SortType sortType, boolean onlyAvailable) {
        return onlyAvailable
                ? roomService.getSortedAvailableRooms(sortType)
                : roomService.getSortedRooms(sortType);
    }

    public List<Client> getRoomHistory(int roomNumber) {
        Queue<Client> history = roomHistory.get(roomNumber);
        return history != null ? new ArrayList<>(history) : Collections.emptyList();
    }

    public List<RoomBooking> getAllActiveBookings(SortType sortType) {
        return orderService.getActiveBookingsSorted(sortType);
    }

    public List<RoomBooking> getAllCompletedBookings() {
        return orderService.getCompletedBookings();
    }

    public List<Amenity> findAmenitiesByPriceRange(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0 || minPrice > maxPrice) {
            throw new IllegalArgumentException("Invalid price range");
        }
        return amenityService.findAmenitiesByPriceRange(minPrice, maxPrice);
    }

    public boolean amenityExists(String amenityName) {
        Objects.requireNonNull(amenityName, "Amenity name cannot be null");
        return amenityService.amenityExists(amenityName);
    }

    public void deleteAmenity(String amenityName) {
        Objects.requireNonNull(amenityName, "Amenity name cannot be null");
        amenityService.deleteAmenity(amenityName);
    }

    public List<AmenityOrder> getClientAmenitiesSorted(Client client, SortType sortType) {
        return orderService.getAmenityOrdersSorted(sortType).stream()
                .filter(order -> order.getClientId().equals(client.getId()))
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

    public void addAmenityToClient(int roomNumber, Amenity amenity, Date serviceDate) {
        if (roomNumber <= 0) {
            throw new IllegalStateException("Invalid room number");
        }
        orderService.addAmenityToBooking(roomNumber, amenity, serviceDate);
    }

    public List<RoomBooking> getLastThreeBookingsForRoom(int roomNumber) {
        return orderService.getLastThreeBookingsForRoom(roomNumber);
    }

    public List<Room> getAvailableRoomsByDate(Date date) {
        return roomService.getAvailableRoomsByDate(date);
    }

    public double calculateRoomPayment(int roomNumber, Date endDate) {
        return roomService.calculateStayCost(roomNumber, endDate) +
                orderService.calculateAmenityCost(roomNumber);
    }

    public double calculateTotalRevenue() {
        return orderService.calculatingTotalIncome();
    }

    public int getAvailableRoomsCount() {
        return roomService.countAvailableRooms();
    }

    public boolean isRoomAvailable(int number) {
        return roomService.isRoomAvailable(number);
    }

    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    public int getClientCount() {
        return clientService.getClientCount();
    }

    public void exportRoomsToCsv(String filePath) throws DataExportException {
        roomCsvService.exportCsv(roomService.getAllRooms(), filePath);
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
            amenityService.findAmenityByName(amenity.getName()).ifPresentOrElse(
                    existing -> {
                        existing.setPrice(amenity.getPrice());
                        amenityService.updateAmenity(existing);
                    },
                    () -> amenityService.addAmenity(amenity)
            );
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
            if (clientService.findClientById(booking.getClientId()).isEmpty()) {
                clientService.registerClient(booking.getClient());
            }
            if (roomService.findRoom(booking.getRoom().getNumberRoom()).isEmpty()) {
                roomService.addRoom(booking.getRoom());
            }
            orderService.createRoomBooking(booking.getClient(), booking.getRoom(),
                    booking.getCheckInDate(), booking.getCheckOutDate());
        }
        return importedBookings;
    }

    public void exportAmenityOrdersToCsv(String filePath) throws DataExportException {
        List<AmenityOrder> orders = new ArrayList<>(orderService.getAmenityOrdersSorted(SortType.NONE));
        amenityOrderCsvService.exportCsv(orders, filePath);
    }

    public List<AmenityOrder> importAmenityOrdersFromCsv(String filePath) throws DataImportException {
        List<AmenityOrder> importedOrders = amenityOrderCsvService.importCsv(filePath);
        for (AmenityOrder order : importedOrders) {
            if (clientService.findClientById(order.getClientId()).isEmpty()) {
                clientService.registerClient(order.getClient());
            }
            if (amenityService.findAmenityByName(order.getAmenity().getName()).isEmpty()) {
                amenityService.addAmenity(order.getAmenity());
            }
            orderService.addAmenityToBooking(
                    order.getClient().getRoom().getNumberRoom(),
                    order.getAmenity(),
                    order.getServiceDate()
            );
        }
        return importedOrders;
    }

    public void clearAll() {
        Arrays.asList(amenityService, clientService, roomService, orderService)
                .forEach(IClearable::clear);
        roomHistory.clear();
    }
}