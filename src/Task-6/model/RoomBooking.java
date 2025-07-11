package model;

import java.util.*;

public class RoomBooking extends Order {
    private Room room;
    private Date checkOutDate;
    private double totalPrice;
    private final List<AmenityOrder> services;

    public RoomBooking(String id, Client client, Room room, double totalPrice, Date checkOutDate) {
        super(id, client, totalPrice, new Date(), checkOutDate);
        setRoom(room);
        setCheckOutDate(checkOutDate);
        setTotalPrice(totalPrice);
        this.services = new ArrayList<>();
    }

    public RoomBooking(Client client, Room room, double totalPrice, Date checkOutDate) {
        super(client, totalPrice, checkOutDate);
        setRoom(room);
        setCheckOutDate(checkOutDate);
        this.services = new ArrayList<>();
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

    @Override
    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<AmenityOrder> getServices() {
        return Collections.unmodifiableList(services);
    }

    public void addService(AmenityOrder service) {
        services.add(Objects.requireNonNull(service));
        setTotalPrice(getTotalPrice() + service.getAmenity().getPrice());
    }
}