package model;
import enums.RoomCondition;

import java.util.Date;

public class Room {
    private int numberRoom;
    private static int roomCount = 0;
    private boolean isAvailable;
    private RoomCondition roomCondition;

    private double priceForDay;
    private Date availableDate;
    private int capacity;
    private int stars;

    public Room(int numberRoom, double priceForDay, int capacity) {
        if (priceForDay <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.numberRoom = numberRoom;
        ++roomCount;
        isAvailable = true;
        roomCondition = RoomCondition.READY;
        this.priceForDay = priceForDay;
    }



    public void clearRoom(){
        isAvailable = true;
        roomCondition = RoomCondition.CLEANING_REQUIRED;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void needRepair(){
        roomCondition = RoomCondition.ON_REPAIR;
        isAvailable = false;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public boolean getEmpty() {
        return isAvailable;
    }

    public void changeAvailable(){
        this.isAvailable = !isAvailable;
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

    public Date getAvailableDate(){
        return availableDate;
    }

    public RoomCondition getRoomCondition() {
        return roomCondition;
    }


    public void setRoomCondition(RoomCondition status) {
        this.roomCondition = status;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getStars() {
        return stars;
    }


    @Override
    public String toString() {
        return "model.Room{" +
                "numberRoom=" + numberRoom +
                ", isAvailable=" + isAvailable +
                ", status=" + roomCondition +
                ", priceForDay=" + priceForDay +
                '}';
    }
}
