package model;

import comparator.Room.CapacityComparator;
import comparator.Room.PriceComparator;
import comparator.Room.StarsComparator;
import enums.RoomCondition;
import enums.SortType;
import java.util.*;
import java.util.stream.Collectors;

public class RoomCol {
    private final Map<Integer, Room> roomsMap;

    public RoomCol() {
        this.roomsMap = new HashMap<>();
    }

    public void createRoom(Room room) {
        if (room == null) {
            System.out.println("Ошибка: Комната не может быть null");
            return;
        }
        if (roomsMap.containsKey(room.getNumberRoom())) {
            System.out.println("Комната с номером " + room.getNumberRoom() + " уже существует");
            return;
        }
        roomsMap.put(room.getNumberRoom(), room);
        System.out.println("Комната " + room.getNumberRoom() + " успешно добавлена");
    }

    public Map<Integer, Room> getSortedRooms(SortType sortType) {
        return getSortedRooms(sortType, false);
    }

    public Map<Integer, Room> getSortedRooms(SortType sortType, boolean onlyAvailable) {
        Comparator<Room> comparator = getRoomComparator(sortType);

        return roomsMap.values().stream()
                .filter(room -> !onlyAvailable || room.getEmpty())
                .sorted(comparator)
                .collect(Collectors.toMap(
                        Room::getNumberRoom,
                        room -> room,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
    }

    private Comparator<Room> getRoomComparator(SortType sortType) {
        return switch (sortType) {
            case CAPACITY -> new CapacityComparator();
            case PRICE -> new PriceComparator();
            case STARS -> new StarsComparator();
            case NONE -> (a,b) -> 0;
            default -> throw new IllegalArgumentException("Неизвестный тип сортировки: " + sortType);
        };
    }

    public void markRoomOccupied(Room room) {
        if (room != null) {
            room.changeAvailable();
        }
    }

    public int getNumberAvailableRooms() {
        return (int) roomsMap.values().stream()
                .filter(Room::getEmpty)
                .count();
    }

    public void clearRoom(int roomNumber) {
        Room room = roomsMap.get(roomNumber);
        if (room != null) {
            room.clearRoom();
        } else {
            System.out.println("Комната с номером " + roomNumber + " не найдена");
        }
    }

    public double getPayment(Client client, Room room, Date dateEnd) {
        if (room == null || dateEnd == null) return 0.0;

        Room targetRoom = roomsMap.get(room.getNumberRoom());
        if (targetRoom == null) return 0.0;

        Date now = new Date();
        return targetRoom.getPriceForDay() * calculateNumberOfDays(now, dateEnd);
    }

    private double calculateNumberOfDays(Date dateIn, Date dateOut) {
        long diffInMillis = dateOut.getTime() - dateIn.getTime();
        double days = (double) diffInMillis / (1000 * 60 * 60 * 24);
        return Math.ceil(days);
    }

    public void deleteRoom(Room room) {
        if (room != null) {
            roomsMap.remove(room.getNumberRoom());
        }
    }

    public List<Room> getAvailableRoomsByDate(Date date) {
        return roomsMap.values().stream()
                .filter(room -> date.equals(room.getAvailableDate()))
                .collect(Collectors.toList());
    }

    public boolean isRoomAvailable(int numberRoom) {
        Room room = roomsMap.get(numberRoom);
        return room != null && room.getEmpty();
    }

    public void changeRoomPrice(Room room, double newPrice) {
        if (room != null) {
            Room targetRoom = roomsMap.get(room.getNumberRoom());
            if (targetRoom != null) {
                targetRoom.setPriceForDay(newPrice);
            }
        }
    }

    public void changeRoomCondition(Room room, RoomCondition newStatus) {
        if (room != null && newStatus != null) {
            Room targetRoom = roomsMap.get(room.getNumberRoom());
            if (targetRoom != null) {
                targetRoom.setRoomCondition(newStatus);
            }
        }
    }

    public boolean containsRoom(Room room) {
        return room != null && roomsMap.containsKey(room.getNumberRoom());
    }

    public Map<Integer, Room> getAllRooms() {
        return Collections.unmodifiableMap(roomsMap);
    }

    public String getRoomDetails(int numberRoom) {
        Room room = roomsMap.get(numberRoom);
        return room != null ? room.toString() : "Комната не найдена";
    }
}