package hotelsystem.repository;

import hotelsystem.Utils.HotelConfig;
import hotelsystem.dependencies.annotation.Component;

import hotelsystem.enums.RoomCondition;
import hotelsystem.enums.SortType;
import hotelsystem.interfaceClass.*;
import hotelsystem.model.Client;
import hotelsystem.model.Room;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RoomRepository implements IRoomRepository {

    private final Map<Integer, Room> rooms;
    private final Map<Integer, Queue<Client>> roomHistory;

    public RoomRepository() {
        this.rooms = new HashMap<>();
        this.roomHistory = new HashMap<>();
    }

    @Override
    public void addRoom(Room room) {
        Objects.requireNonNull(room, "Room cannot be null");
        if (rooms.containsKey(room.getNumberRoom())) {
            throw new IllegalArgumentException("Room " + room.getNumberRoom() + " already exists");
        }
        rooms.put(room.getNumberRoom(), room);
    }

    @Override
    public void addClientToRoomHistory(int roomNumber, Client client) {
        Objects.requireNonNull(client, "Client cannot be null");
        Room room = rooms.get(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Room " + roomNumber + " not found");
        }

        roomHistory.computeIfAbsent(roomNumber, k -> new LinkedList<>()).add(client);

        Queue<Client> history = roomHistory.get(roomNumber);
        while (history.size() > HotelConfig.getMaxHistoryEntries()) {
            history.poll();
        }
    }

    @Override
    public Optional<Room> findRoomByNumber(int number) {
        return Optional.ofNullable(rooms.get(number));
    }

    @Override
    public void markRoomOccupied(Room room) {
        Objects.requireNonNull(room, "Room cannot be null");
        room.changeAvailability();
    }

    @Override
    public void clearRoom(int roomNumber) {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Room " + roomNumber + " not found");
        }
        room.clearRoom();
    }

    @Override
    public void changeRoomCondition(int number, RoomCondition newStatus) {
        Objects.requireNonNull(newStatus, "Status cannot be null");
        Room target = rooms.get(number);
        if (target != null) {
            target.setRoomCondition(newStatus);
        }
    }

    @Override
    public void changeRoomPrice(int number, double newPrice) {
        Room target = rooms.get(number);
        if (target != null) {
            target.setPriceForDay(newPrice);
        }
    }

    @Override
    public void assignClientToRoom(int roomNumber, String clientId, Date availableDate) {
        Objects.requireNonNull(clientId, "Client ID cannot be null");
        Objects.requireNonNull(availableDate, "Available date cannot be null");
        Room room = rooms.get(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Room with number " + roomNumber + " does not exist");
        }
        room.setClientIdAndDateAvailable(clientId, availableDate);
    }

    @Override
    public String getAssignedClientId(int roomNumber) {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Room with number " + roomNumber + " does not exist");
        }
        return room.getClientId();
    }

    @Override
    public Map<Integer, Room> getAllRooms() {
        return Collections.unmodifiableMap(rooms);
    }

    @Override
    public Map<Integer, Room> getAvailableRooms() {
        return filterAndSortRooms(SortType.NONE, true);
    }

    @Override
    public Map<Integer, Room> getRoomsByCondition(RoomCondition condition) {
        Objects.requireNonNull(condition, "Condition cannot be null");
        return rooms.values().stream()
                .filter(room -> room.getRoomCondition() == condition)
                .collect(Collectors.toMap(
                        Room::getNumberRoom,
                        room -> room,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    @Override
    public Map<Integer, Room> getAvailableRoomsByDate(Date date) {
        Objects.requireNonNull(date, "Date cannot be null");
        return rooms.values().stream()
                .filter(room -> {
                    Date availableDate = room.getAvailableDate();
                    return room.isAvailable() ||
                            (availableDate != null && !availableDate.after(date));
                })
                .collect(Collectors.toMap(
                        Room::getNumberRoom,
                        room -> room,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    @Override
    public Map<Integer, Room> getSortedRooms(SortType sortType) {
        return filterAndSortRooms(sortType, false);
    }

    @Override
    public Map<Integer, Room> getSortedAvailableRooms(SortType sortType) {
        return filterAndSortRooms(sortType, true);
    }

    private Map<Integer, Room> filterAndSortRooms(SortType sortType, boolean onlyAvailable) {
        Objects.requireNonNull(sortType, "Sort type cannot be null");
        Comparator<Room> comparator = getComparator(sortType);
        return rooms.values().stream()
                .filter(room -> !onlyAvailable || room.isAvailable())
                .sorted(comparator)
                .collect(Collectors.toMap(
                        Room::getNumberRoom,
                        room -> room,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    private Comparator<Room> getComparator(SortType sortType) {
        return switch (sortType) {
            case CAPACITY -> Comparator.comparingInt(Room::getCapacity);
            case PRICE -> Comparator.comparingDouble(Room::getPriceForDay);
            case STARS -> Comparator.comparingInt(Room::getStars);
            case TYPE -> Comparator.comparing(Room::getType);
            case NONE -> (a, b) -> 0;
            default -> throw new IllegalArgumentException("Unknown sort type: " + sortType);
        };
    }

    @Override
    public boolean isRoomAvailable(int roomNumber) {
        Room room = rooms.get(roomNumber);
        return room != null && room.isAvailable();
    }

    @Override
    public int countAvailableRooms() {
        return (int) rooms.values().stream()
                .filter(Room::isAvailable)
                .count();
    }

    @Override
    public double calculateStayCost(int roomNumber, Date endDate) {
        Objects.requireNonNull(endDate, "End date cannot be null");
        Room room = rooms.get(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Room " + roomNumber + " does not exist");
        }
        Date now = new Date();
        if (endDate.before(now)) {
            throw new IllegalArgumentException("End date cannot be in the past");
        }
        return room.getPriceForDay() * calculateDaysBetween(now, endDate);
    }

    private double calculateDaysBetween(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        return Math.ceil((double) diff / (1000 * 60 * 60 * 24));
    }

    @Override
    public List<Client> getRoomHistory(int roomNumber) {
        Queue<Client> history = roomHistory.get(roomNumber);
        return history != null ? new ArrayList<>(history) : Collections.emptyList();
    }

    @Override
    public String getRoomDetails(int roomNumber) {
        Room room = rooms.get(roomNumber);
        return room != null ? room.toString() : "Room not found";
    }
    @Override
    public void clearAll() {
        rooms.clear();
    }
}