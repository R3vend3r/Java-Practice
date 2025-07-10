package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class RoomBooking extends Order {
    private final Room room;
    private Date checkOutDate;
    private final double totalPrice;
    private final List<AmenityOrder> services;

    public RoomBooking(Client client, Room room, double totalPrice,  Date checkOutDate) {
        super(client, new Date(), checkOutDate);
        this.room = room;
        this.checkOutDate = checkOutDate;
        this.services = new ArrayList<>();
        this.totalPrice = totalPrice;
    }

    public Room getRoom() { return room; }
    public Date getCheckOutDate() { return checkOutDate; }
    public List<AmenityOrder> getServices() { return Collections.unmodifiableList(services); }

    public void addService(AmenityOrder service) {
        services.add(service);
        setTotalPrice(getTotalPrice() + service.getAmenity().getPrice());
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}