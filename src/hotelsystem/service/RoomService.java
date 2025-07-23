package hotelsystem.service;

import hotelsystem.Exception.DatabaseException;
import hotelsystem.dependencies.annotation.Component;
import hotelsystem.dependencies.annotation.Inject;
import hotelsystem.enums.RoomCondition;
import hotelsystem.enums.RoomType;
import hotelsystem.enums.SortType;
import hotelsystem.interfaceClass.IClearable;
import hotelsystem.model.Client;
import hotelsystem.model.Room;
import hotelsystem.repo.dao.RoomDAO;
import hotelsystem.repository.RoomRepository;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RoomService implements IClearable {
    @Inject
    private RoomDAO roomDAO;
    @Inject
    private RoomRepository roomRepository;

    public void addRoom(Room room) {
        try {
            roomDAO.create(room);
            roomRepository.addRoom(room);
        } catch (DatabaseException e) {
            throw new RuntimeException("Failed to add room", e);
        }
    }

    public Optional<Room> findRoom(int roomNumber) {
        try {
            Room room = roomDAO.findById(roomNumber);
            if (room != null) {
                roomRepository.addRoom(room);
                return Optional.of(room);
            }
            return roomRepository.findRoom(roomNumber);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find room", e);
        }
    }

    public void updateRoom(Room room) {
        try {
            roomDAO.update(room);
            roomRepository.addRoom(room);
        } catch (DatabaseException e) {
            throw new RuntimeException("Failed to update room", e);
        }
    }

    public List<Room> getAllRooms() {
        try {
            List<Room> rooms = roomDAO.findAll();
            rooms.forEach(roomRepository::addRoom);
            return rooms;
        } catch (DatabaseException e) {
            throw new RuntimeException("Failed to get all rooms", e);
        }
    }


    public List<Room> getAvailableRooms() {
        try {
            List<Room> rooms = roomDAO.findAvailableRooms();
            rooms.forEach(roomRepository::addRoom);
            return rooms;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get available rooms", e);
        }
    }

    public List<Room> getAvailableRoomsByDate(Date date) {
        try {
            List<Room> rooms = roomDAO.findAvailableByDate(date);
            rooms.forEach(roomRepository::addRoom);
            return rooms;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get available rooms by date", e);
        }
    }

    public double calculateStayCost(int roomNumber, Date endDate) {
        return findRoom(roomNumber).map(room -> {
            long days = calculateDaysBetween(new Date(), endDate);
            return room.getPriceForDay() * days;
        }).orElseThrow(() -> new IllegalArgumentException("Room not found"));
    }

    private long calculateDaysBetween(Date start, Date end) {
        long diffInMillis = end.getTime() - start.getTime();
        return (long) Math.ceil(diffInMillis / (1000.0 * 60 * 60 * 24));
    }

    public int countAvailableRooms() {
        return (int) getAllRooms().stream()
                .filter(Room::isAvailable)
                .count();
    }

    public boolean isRoomAvailable(int roomNumber) {
        return findRoom(roomNumber)
                .map(Room::isAvailable)
                .orElse(false);
    }

    public void markRoomOccupied(Room room, Client client) {
        room.setAvailable(false);
        room.setClient(client);
        updateRoom(room);
    }

    public void clearRoom(int roomNumber) {
        findRoom(roomNumber).ifPresent(room -> {
            room.setAvailable(true);
            room.setRoomCondition(RoomCondition.CLEANING_REQUIRED);
            room.setClient(null);
            room.setAvailableDate(null);
            updateRoom(room);
        });
    }

    public void assignClientToRoom(int roomNumber, Client client, Date availableDate) {
        findRoom(roomNumber).ifPresent(room -> {
            room.setClient(client);
            room.setAvailableDate(availableDate);
            room.setAvailable(false);
            updateRoom(room);
        });
    }

//    public void addClientToRoomHistory(int roomNumber, Client client) {
//        roomRepository.addClientToHistory(roomNumber, client);
//    }
//
//    public List<Client> getRoomHistory(int roomNumber) {
//        return roomRepository.getRoomHistory(roomNumber);
//    }


    public List<Room> getSortedRooms(SortType sortType) {
        return sortRooms(getAllRooms(), sortType);
    }

    public List<Room> getSortedAvailableRooms(SortType sortType) {
        return sortRooms(
                getAllRooms().stream().filter(Room::isAvailable).collect(Collectors.toList()),
                sortType
        );
    }

    private List<Room> sortRooms(List<Room> rooms, SortType sortType) {
        Comparator<Room> comparator = switch (sortType) {
            case CAPACITY -> Comparator.comparingInt(Room::getCapacity);
            case PRICE -> Comparator.comparingDouble(Room::getPriceForDay);
            case STARS -> Comparator.comparingInt(Room::getStars);
            case TYPE -> Comparator.comparing(Room::getType);
            default -> (a, b) -> 0;
        };
        return rooms.stream().sorted(comparator).collect(Collectors.toList());
    }

    public void updateRoomStatus(int roomNumber, RoomCondition status) {
        findRoom(roomNumber).ifPresent(room -> {
            room.setRoomCondition(status);
            updateRoom(room);
        });
    }

    public void updateRoomPrice(int roomNumber, double newPrice) {
        findRoom(roomNumber).ifPresent(room -> {
            room.setPriceForDay(newPrice);
            updateRoom(room);
        });
    }

    @Override
    public void clear() {
        try {
            roomDAO.findAll().forEach(room -> {
                try {
                    roomDAO.delete(room.getNumberRoom());
                } catch (DatabaseException e) {
                    throw new RuntimeException(e);
                }
            });
            roomRepository.clear();
        } catch (DatabaseException e) {
            throw new RuntimeException("Failed to clear rooms", e);
        }
    }
}