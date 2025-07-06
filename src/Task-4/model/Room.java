package model;
import enums.RoomCondition;

import java.util.Date;

public class Room {
    private int numberRoom;
    private static int roomCount = 0;
    private boolean isEmpty;
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
        isEmpty = true;
        roomCondition = RoomCondition.READY;
        this.priceForDay = priceForDay;
    }



    public void clearRoom(){
        isEmpty = true;
        roomCondition = RoomCondition.CLEANING_REQUIRED;
    }

    public int getNumberRoom() {
        return numberRoom;
    }

    public void needRepair(){
        roomCondition = RoomCondition.ON_REPAIR;
        isEmpty = false;
    }

    public void setNumberRoom(int numberRoom) {
        this.numberRoom = numberRoom;
    }

    public boolean getEmpty() {
        return isEmpty;
    }

    public void changeAvailable(){
        this.isEmpty = !isEmpty;
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
                ", isEmpty=" + isEmpty +
                ", status=" + roomCondition +
                ", priceForDay=" + priceForDay +
                '}';
    }
}
