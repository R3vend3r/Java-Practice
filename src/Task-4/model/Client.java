package model;
import java.util.UUID;

public class Client {
    private String name;
    private String surname;
    private final String id;

    public Client(String surname, String name) {
        this.surname = surname;
        this.name = name;
        this.id = generateId();
    }

    private String generateId() {
        return "CL-" + UUID.randomUUID().toString().substring(0, 8);
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
        return id;
    }

}
