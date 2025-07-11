package model;

import java.util.*;

public class RoomBooking extends Order {
    private Room room;
    private Date checkOutDate;
    private final List<AmenityOrder> services;

    public RoomBooking(String id, Client client, Room room, Date checkOutDate) {
        super(id, client, new Date(), checkOutDate);
        setRoom(room);
        setCheckOutDate(checkOutDate);
        this.services = new ArrayList<>();
    }

    public RoomBooking(Client client, Room room, Date checkOutDate) {
        this(generateId(), client, room, checkOutDate);
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = Objects.requireNonNull(room, "Room cannot be null");
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = Objects.requireNonNull(checkOutDate, "Check-out date cannot be null");
        setAvailableDate(checkOutDate);
    }

    public List<AmenityOrder> getServices() {
        return Collections.unmodifiableList(services);
    }

    public void addService(AmenityOrder service) {
        services.add(Objects.requireNonNull(service));
        setTotalPrice(getTotalPrice() + service.getAmenity().getPrice());
    }
}