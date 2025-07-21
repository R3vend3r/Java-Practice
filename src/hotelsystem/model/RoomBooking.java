package hotelsystem.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class RoomBooking extends Order {
    @Getter
    private Room room;
    @Getter
    private Date checkOutDate;
    @Setter
    @Getter
    private Date checkInDate;
    private double totalPrice;
    private List<AmenityOrder> services;
    @Getter
    @Setter
    private Client client;

    public RoomBooking() {
    }

    public RoomBooking(String id, Client client, Room room, double totalPrice, Date checkInDate, Date checkOutDate) {
        super(id, client.getId(), totalPrice, checkInDate, checkOutDate);
        setRoom(room);
        setClient(client);
        setCheckOutDate(checkOutDate);
        setTotalPrice(totalPrice);
        setCheckInDate(checkInDate);
        this.services = new ArrayList<>();
    }

    public RoomBooking(Client client, Room room, double totalPrice, Date checkInDate, Date checkOutDate) {
        super(client.getId(), totalPrice,checkInDate, checkOutDate);
        setRoom(room);
        setCheckOutDate(checkOutDate);
        setCheckInDate(checkInDate);
        this.services = new ArrayList<>();
    }

    public void setRoom(Room room) {
        this.room = Objects.requireNonNull(room, "Room cannot be null");
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