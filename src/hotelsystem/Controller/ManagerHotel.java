package hotelsystem.Controller;

import hotelsystem.Utils.HotelConfig;
import hotelsystem.Utils.*;
import hotelsystem.csv.*;
import hotelsystem.Exception.*;
import hotelsystem.dependencies.annotation.Inject;
import hotelsystem.dependencies.annotation.PostConstruct;
import hotelsystem.dependencies.annotation.Variant;
import hotelsystem.enums.RoomCondition;
import hotelsystem.enums.SortType;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import hotelsystem.model.*;
import hotelsystem.service.*;

public class ManagerHotel {
    @Inject
    private  RoomService roomService ;
    @Inject
    private AmenityService amenityService;
    @Inject
    private ClientService clientService;
    @Inject
    private OrderService orderService;
    @Inject
    @Variant("roomCsvService")
    private ICsvService<Room> roomCsvService;
    @Inject
    @Variant("amenityCsvService")
    private ICsvService<Amenity> amenityCsvService;
    @Inject
    @Variant("clientCsvService")
    private ICsvService<Client> clientCsvService;
    @Inject
    @Variant("roomBookingCsvService")
    private ICsvService<RoomBooking> roomBookingCsvService;
    @Inject
    @Variant("amenityOrderCsvService")
    private ICsvService<AmenityOrder> amenityOrderCsvService;
    @Inject
    private HotelConfig hotelConfig;
    private Map<Integer, Queue<Client>> roomHistory = new HashMap<>();


    @PostConstruct
    public void postConstruct() {
        System.out.println("Система Отеля создалась!");
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
        roomService.assignClientToRoom(room.getNumberRoom(), client.getId(), checkOutDate);
        roomService.markRoomOccupied(room);
        clientService.assignRoomToClient(client.getId(), room.getNumberRoom());

        // Добавляем клиента в историю комнаты
        roomHistory.computeIfAbsent(room.getNumberRoom(), k -> new LinkedList<>()).add(client);
        // Ограничиваем размер истории согласно конфигурации
        Queue<Client> history = roomHistory.get(room.getNumberRoom());
        while (history != null && history.size() > HotelConfig.getMaxHistoryEntries()) {
            history.poll();
        }
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
        if (HotelConfig.isRoomStatusChangeEnabled()) {
            roomService.updateRoomStatus(number, status);
        } else {
            System.out.println("Изменение статуса комнаты запрещено конфигурацией.");
        }
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
    public void saveStateToJson(String filePath) {
        HotelState state = new HotelState(
                roomService.getSortedRooms(SortType.NONE),
                clientService.getAllClients(),
                amenityService.getAllAmenities(),
                orderService.getActiveBookingsSorted(SortType.NONE),
                orderService.getCompletedBookings(),
                orderService.getAmenityOrdersSorted(SortType.NONE),
                roomHistory
        );
        HotelJsonUtil.saveState(state, filePath);
    }

    public void loadStateFromJson(String filePath) {
        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Путь к файлу не может быть пустым");
        }

        HotelState state = HotelJsonUtil.loadState(filePath);
        if (state == null) {
            System.out.println("Нет данных для загрузки");
            return;
        }

        roomService.clear();
        clientService.clear();
        amenityService.clear();
        orderService.clear();
        roomHistory.clear();

        state.getRooms().values().forEach(roomService::addRoom);
        state.getClients().forEach(clientService::registerClient);
        state.getAmenities().forEach(amenityService::addAmenity);
        state.getActiveBookings().forEach(booking ->
                orderService.createRoomBooking(booking.getClient(), booking.getRoom(), booking.getCheckInDate(), booking.getCheckOutDate())
        );
        state.getAmenityOrders().forEach(order ->
                orderService.addAmenityToBooking(
                        order.getClient().getRoomNumber(),
                        order.getAmenity(),
                        order.getServiceDate()
                ));
        roomHistory.putAll(state.getRoomHistory());
    }
    public void clearAll() {
        Arrays.asList(
                amenityService,
                clientService,
                roomService,
                orderService
        ).forEach(service -> {
            if (service != null) {
                service.clear();
            }
        });
    }
}