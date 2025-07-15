package hotelsystem.model;

import hotelsystem.Utils.HotelConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import hotelsystem.enums.RoomCondition;
import hotelsystem.enums.RoomType;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Room implements Serializable {
    @Getter
    private String id;
    @Setter
    @Getter
    private int numberRoom;
    @Getter
    @JsonProperty("available")
    private boolean isAvailable;
    @Getter
    private RoomCondition roomCondition;
    @Setter
    @Getter
    private RoomType type;
    @Getter
    private double priceForDay;
    @Getter
    private Date availableDate;
    @Getter
    private int capacity;
    @Getter
    private int stars;
    @Setter
    @Getter
    private String clientId;
    private final Queue<Client> clientHistory = new LinkedList<>();

    public Room() {
    }

    public Room(String id, int number, RoomType type, double priceForDay, int capacity,
                RoomCondition condition, int stars) {
        setId(id);
        setNumberRoom(number);
        setType(type);
        setPriceForDay(priceForDay);
        setCapacity(capacity);
        setRoomCondition(condition);
        setStars(stars);
        this.isAvailable = true;
    }

    public Room(int number, RoomType type, double priceForDay, int capacity) {
        this(generateId(), number, type, priceForDay, capacity,
                RoomCondition.READY, 3);
    }

    public void addClientToHistory(Client client) {
        Objects.requireNonNull(client, "Client cannot be null");
        clientHistory.add(client);
        // Ограничиваем размер истории согласно конфигурации
        while (clientHistory.size() > HotelConfig.getMaxHistoryEntries()) {
            clientHistory.poll();
        }
    }

    private static String generateId() {
        return "RM-" + System.currentTimeMillis();
    }

    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "Room ID cannot be null");
        if (id.isBlank()) {
            throw new IllegalArgumentException("Room ID cannot be blank");
        }
    }

    public void clearRoom() {
        isAvailable = true;
        roomCondition = RoomCondition.CLEANING_REQUIRED;
        clientId = null;
        availableDate = null;
    }

    public void changeAvailability() {
        this.isAvailable = !isAvailable;
    }

    public void setClientIdAndDateAvailable(String clientId, Date availableDate) {
        this.clientId = Objects.requireNonNull(clientId);
        this.availableDate = Objects.requireNonNull(availableDate);
    }

    public void setPriceForDay(double priceForDay) {
        if (priceForDay <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.priceForDay = priceForDay;
    }

    public void setRoomCondition(RoomCondition status) {
        if (!HotelConfig.isRoomStatusChangeEnabled()) {
            throw new IllegalStateException("Изменение статуса комнаты отключено в настройках");
        }
        this.roomCondition = Objects.requireNonNull(status);
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
    }


    public void setStars(int stars) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Stars must be between 1 and 5");
        }
        this.stars = stars;
    }

    public Queue<Client> getClientHistory() {
        return new LinkedList<>(clientHistory);
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