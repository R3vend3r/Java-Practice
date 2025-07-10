package model;

import enums.RoomCondition;
import enums.RoomType;

import java.util.Date;
import java.util.Objects;

public class Room{
    private final int numberRoom;
    private boolean isAvailable;
    private RoomCondition roomCondition;
    private final RoomType type;
    private double priceForDay;
    private Date availableDate;
    private int capacity;
    private int stars;
    private String clientId;

    public Room(int numberRoom, RoomType type, double priceForDay, int capacity) {
        if (priceForDay <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        this.numberRoom = numberRoom;
        this.type = Objects.requireNonNull(type);
        this.isAvailable = true;
        this.roomCondition = RoomCondition.READY;
        this.priceForDay = priceForDay;
        this.capacity = capacity;
        this.stars = 3; // default value
    }

    public void clearRoom() {
        isAvailable = true;
        roomCondition = RoomCondition.CLEANING_REQUIRED;
        clientId = null;
        availableDate = null;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void changeAvailability() {
        this.isAvailable = !isAvailable;
    }

    public RoomType getType() {
        return type;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientIdAndDateAvailable(String clientId, Date availableDate) {
        this.clientId = Objects.requireNonNull(clientId);
        this.availableDate = Objects.requireNonNull(availableDate);
    }

    public double getPriceForDay() {
        return priceForDay;
    }

    public void setPriceForDay(double priceForDay) {
        if (priceForDay <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.priceForDay = priceForDay;
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public RoomCondition getRoomCondition() {
        return roomCondition;
    }

    public void setRoomCondition(RoomCondition status) {
        this.roomCondition = Objects.requireNonNull(status);
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Stars must be between 1 and 5");
        }
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "Room{" +
                "number=" + numberRoom +
                ", type=" + type +
                ", available=" + isAvailable +
                ", condition=" + roomCondition +
                ", price=" + priceForDay +
                ", capacity=" + capacity +
                ", stars=" + stars +
                '}';
    }
}