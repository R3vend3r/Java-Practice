package hotelsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Objects;

@Getter
@Entity
@Table(name = "amenity_orders")
public class AmenityOrder extends Order {

    @Setter
    @Id
    @Column(name = "id")
    private String id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "amenity_id", nullable = false)
    private Amenity amenity;

    @Column(name = "service_date", nullable = false)
    private Date serviceDate;

    @Setter
    @Column(name = "total_price", nullable = false)
    private double price;

    @Setter
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;


    public AmenityOrder() {}

    public AmenityOrder(String id, Client client, double totalPrice,
                        Amenity amenity, Date serviceDate) {
        super(id, client.getId(), totalPrice, new Date(), serviceDate);
        setId(id);
        setAmenity(amenity);
        setServiceDate(serviceDate);
        setClient(client);
        setPrice(totalPrice);
    }


    public void setServiceDate(Date serviceDate) {
        this.serviceDate = Objects.requireNonNull(serviceDate, "Service date cannot be null");
        setAvailableDate(serviceDate);
    }
}