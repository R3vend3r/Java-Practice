package hotelsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
@Table(name = "clients")
public class Client implements Serializable {
    @Id
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "room_number")
    private Room room;

    public Client() {
    }

    public Client(String id, String name, String surname, Room room) {
        setId(id);
        setName(name);
        setSurname(surname);
        setRoom(room);
    }

    public Client(String name, String surname) {
        this(generateId(), name, surname, null);
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


    @Override
    public String toString() {
        return String.format("Client[id=%s, name=%s, surname=%s, room=%s]",
                id, name, surname, (room != null ? room.getNumberRoom() : "null"));
    }
}