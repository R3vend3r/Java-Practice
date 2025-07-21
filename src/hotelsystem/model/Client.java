package hotelsystem.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Client implements Serializable {
    private String id;
    private String name;
    private String surname;
    private int roomNumber;

    public Client() {
    }

    public Client(String id, String name, String surname, int roomNumber) {
        setId(id);
        setName(name);
        setSurname(surname);
        setRoomNumber(roomNumber);
    }

    public Client(String name, String surname) {
        this(generateId(), name, surname, -1);
    }

    private static String generateId() {
        return "CL-" + UUID.randomUUID().toString().substring(0, 8);
    }

    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "Client ID cannot be null");
        if (id.isBlank()) {
            throw new IllegalArgumentException("Client ID cannot be blank");
        }
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank");
        }
    }

    public void setSurname(String surname) {
        this.surname = Objects.requireNonNull(surname, "Surname cannot be null");
        if (surname.isBlank()) {
            throw new IllegalArgumentException("Surname cannot be blank");
        }
    }

    public void setRoomNumber(int roomNumber) {
        if (roomNumber < -1) {
            throw new IllegalArgumentException("Room number cannot be negative");
        }
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return String.format("Client[id=%s, name=%s, surname=%s, room=%d]",
                id, name, surname, roomNumber);
    }
}