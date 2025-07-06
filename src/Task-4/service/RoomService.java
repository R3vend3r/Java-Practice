package service;

import enums.RoomCondition;
import enums.SortType;
import model.Client;
import model.Room;
import model.RoomCol;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class RoomService {
    private final RoomCol roomCol;

    public RoomService() {
        this.roomCol = new RoomCol();
    }

    public void addRoom(Room newRoom) {
        roomCol.createRoom(newRoom);
    }

    public void clearRoom(int roomNumber) {
        roomCol.clearRoom(roomNumber);
    }

    public void deleteRoom(Room room) {
        roomCol.deleteRoom(room);
    }

    public void changeRoomPrice(Room room, double newPrice) {
        roomCol.changeRoomPrice(room, newPrice);
    }

    public void changeRoomCondition(Room room, RoomCondition status) {
        roomCol.changeRoomCondition(room, status);
    }

    public List<Room> getAvailableRoomsByDate(Date date) {
        return roomCol.getAvailableRoomsByDate(date);
    }

    public Map<Integer, Room> getSortedRooms(SortType sortType) {
        return roomCol.getSortedRooms(sortType);
    }

    public Map<Integer, Room> getSortedAvailableRooms(SortType sortType) {
        return roomCol.getSortedRooms(sortType, true);
    }

    public String getRoomDetails(int roomNumber) {
        return roomCol.getRoomDetails(roomNumber);
    }

    public double calculateRoomPayment(Client client, Room room, Date dateEnd) {
        return roomCol.getPayment(client, room, dateEnd);
    }

    public int getNumberAvailableRooms() {
        return roomCol.getNumberAvailableRooms();
    }

    public boolean isRoomAvailable(int numberRoom) {
        return roomCol.isRoomAvailable(numberRoom);
    }

    public void markRoomOccupied(Room room) {
        roomCol.markRoomOccupied(room);
    }

    public boolean getRoom(Room room) {
        return roomCol.containsRoom(room);
    }
}