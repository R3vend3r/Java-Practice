package hotelsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "room_bookings")
public class RoomBooking extends Order {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_number", referencedColumnName = "number")
    private Room room;

    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "check_out_date", nullable = false)
    private Date checkOutDate;

    @Column(name = "check_in_date", nullable = false)
    private Date checkInDate;

    @Column(name = "total_price", nullable = false)
    private double totalPrice;

    @Transient
    private List<AmenityOrder> services;


    public RoomBooking() {
    }

    public RoomBooking(String id, Client client, Room room, double totalPrice, Date checkInDate, Date checkOutDate) {
        super(id, client.getId(), totalPrice, checkInDate, checkOutDate);
        Objects.requireNonNull(client, "Client cannot be null");
        Objects.requireNonNull(room, "Room cannot be null");
        setId(id);
        setRoom(room);
        setClient(client);
        setCheckOutDate(checkOutDate);
        setTotalPrice(totalPrice);
        setCheckInDate(checkInDate);
        this.services = new ArrayList<>();
    }

    public RoomBooking(Client client, Room room, double totalPrice, Date checkInDate, Date checkOutDate) {
        super(client.getId(), totalPrice,checkInDate, checkOutDate);
        Objects.requireNonNull(client, "Client cannot be null");
        Objects.requireNonNull(room, "Room cannot be null");
        setRoom(room);
        setClient(client);
        setCheckOutDate(checkOutDate);
        setCheckInDate(checkInDate);
        this.services = new ArrayList<>();
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