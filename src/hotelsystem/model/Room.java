package hotelsystem.model;

import hotelsystem.Utils.HotelConfig;
import com.fasterxml.jackson.annotation.JsonProperty;
import hotelsystem.dependencies.annotation.ConfigProperty;
import hotelsystem.dependencies.annotation.Inject;
import hotelsystem.enums.RoomCondition;
import hotelsystem.enums.RoomType;
import hotelsystem.model.converters.RoomConditionConverter;
import hotelsystem.model.converters.RoomTypeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "rooms")
public class Room implements Serializable {
    @Setter
    @Getter
    @Id
    @Column(name = "number", nullable = false)
    private Integer numberRoom;

    @Setter
    @Getter
    @JsonProperty("available")
    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    @Getter
    @Convert(converter = RoomConditionConverter.class)
    @Column(name = "condition", nullable = false)
    private RoomCondition roomCondition;

    @Setter
    @Getter
    @Convert(converter = RoomTypeConverter.class)
    @Column(nullable = false)
    private RoomType type;

    @Getter
    @Column(name = "price", nullable = false)
    private double priceForDay;

    @Setter
    @Getter
    @Column(name = "available_date", nullable = true)
    private Date availableDate;

    @Getter
    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Getter
    @Column(name = "stars", nullable = false)
    private int stars;

    @Setter
    @Getter
    @OneToOne(mappedBy = "room")
    private Client client;


//    @ConfigProperty(propertyName = "hotel.room.history.max_entries")
//    private int maxHistoryEntries;

//    @Getter
//    @ElementCollection
//    @CollectionTable(name = "room_client_history", joinColumns = @JoinColumn(name = "number"))
//    @Column(name = "client_id") // Укажи имя столбца для хранения client.id
//    private List<String> clientHistory = new ArrayList<>(); // Храним только id клиентов


    public Room() {
//        this.clientHistory = new LinkedList<>();
    }

    public Room(int number, RoomType type, double priceForDay, int capacity,
                RoomCondition condition, int stars) {
        setNumberRoom(number);
        setType(type);
        setPriceForDay(priceForDay);
        setCapacity(capacity);
        setRoomCondition(condition);
        setStars(stars);
        this.isAvailable = true;
    }

    public Room(int number, RoomType type, double priceForDay, int capacity) {
        this(number, type, priceForDay, capacity,
                RoomCondition.READY, 3);
    }

//    public void addClientToHistory(Client client) {
//        Objects.requireNonNull(client, "Client cannot be null");
//        clientHistory.add(client.getId());
//        trimHistory();
//    }
//
//    private void trimHistory() {
//        while (clientHistory.size() > hotelConfig.getMaxHistoryEntries()) {
//            clientHistory.remove(0);
//        }
//    }


    public void clearRoom() {
        isAvailable = true;
        roomCondition = RoomCondition.CLEANING_REQUIRED;
        client = null;
        availableDate = null;
    }

//    public void setClientIdAndDateAvailable(String clientId, Date availableDate) {
//        this.clientId = Objects.requireNonNull(clientId);
//        this.availableDate = Objects.requireNonNull(availableDate);
//    }

    public void setPriceForDay(double priceForDay) {
        if (priceForDay <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.priceForDay = priceForDay;
    }

    public void setRoomCondition(RoomCondition status) {
//        if (hotelConfig != null && !hotelConfig.isRoomStatusChangeEnabled()) {
//            throw new IllegalStateException("Изменение статуса комнаты отключено в настройках");
//        }
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