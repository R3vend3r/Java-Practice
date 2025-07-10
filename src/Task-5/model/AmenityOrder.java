package model;

import interfaceClass.*;
import java.util.Date;

public class AmenityOrder extends Order {
    private final Amenity amenity;
    private final Date serviceDate;

    public AmenityOrder(Client client, Amenity amenity, Date serviceDate) {
        super(client, new Date(), serviceDate);
        this.amenity = amenity;
        this.serviceDate = serviceDate;
        setTotalPrice(amenity.getPrice());
    }

    public Amenity getAmenity() { return amenity; }
    public Date getServiceDate() { return serviceDate; }
}