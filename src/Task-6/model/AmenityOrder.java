package model;

import java.util.Date;
import java.util.Objects;

public class AmenityOrder extends Order {
    private Amenity amenity;
    private Date serviceDate;

    public AmenityOrder(String id, Client client, Amenity amenity, Date serviceDate) {
        super(id, client, new Date(), serviceDate);
        setAmenity(amenity);
        setServiceDate(serviceDate);
        setTotalPrice(amenity.getPrice());
    }

    public AmenityOrder(Client client, Amenity amenity, Date serviceDate) {
        this(generateId(), client, amenity, serviceDate);
    }

    public Amenity getAmenity() {
        return amenity;
    }

    public void setAmenity(Amenity amenity) {
        this.amenity = Objects.requireNonNull(amenity, "Amenity cannot be null");
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = Objects.requireNonNull(serviceDate, "Service date cannot be null");
        setAvailableDate(serviceDate);
    }
}