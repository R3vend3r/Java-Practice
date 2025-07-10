package model;

import java.util.UUID;

public class Client {
    private String name;
    private String surname;
    private final String clientId;
    private int roomNumber;

    public Client(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.clientId = "CL-" + UUID.randomUUID().toString().substring(0, 8);
        this.roomNumber = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getClientId() {
        return clientId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        if (roomNumber < -1) {
            throw new IllegalArgumentException("Room number cannot be negative");
        }
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return String.format("Client[name=%s, surname=%s, id=%s, room=%d]",
                name, surname, clientId, roomNumber);
    }
}