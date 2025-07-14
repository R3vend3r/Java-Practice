package model;

import java.util.*;

public class RoomBooking extends Order {
    private Room room;
    private Date checkOutDate;
    private Date checkInDate;
    private double totalPrice;
    private List<AmenityOrder> services;

    public RoomBooking() {
    }

    public RoomBooking(String id, Client client, Room room, double totalPrice, Date checkOutDate, Date checkInDate) {
        super(id, client, totalPrice, checkInDate, checkOutDate);
        setRoom(room);
        setCheckOutDate(checkOutDate);
        setTotalPrice(totalPrice);
        setCheckInDate(checkInDate);
        this.services = new ArrayList<>();
    }

    public RoomBooking(Client client, Room room, double totalPrice, Date checkInDate, Date checkOutDate) {
        super(client, totalPrice,checkInDate, checkOutDate);
        setRoom(room);
        setCheckOutDate(checkOutDate);
        setCheckInDate(checkInDate);
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

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
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