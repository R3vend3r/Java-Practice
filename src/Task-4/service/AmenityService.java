package service;

import model.Amenity;
import model.AmenityCol;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AmenityService {
    private final AmenityCol amenityCol;

    public AmenityService() {
        this.amenityCol = new AmenityCol();
    }

    public void addAmenity(Amenity amenity) {
        amenityCol.addAmenity(amenity);
    }

    public boolean hasAmenity(Amenity amenity) {
        return amenityCol.containsAmenity(amenity);
    }

    public void changeAmenityPrice(Amenity amenity, double newPrice) {
        if (amenity != null && amenityCol.containsAmenity(amenity)) {
            amenity.setPrice(newPrice);
        }
    }

    public List<Amenity> getAllAmenity() {
        return amenityCol.getAllAmenities();
    }

    public List<Amenity> getAmenitiesSortedByPrice() {
        return amenityCol.getSortedAmenities(Comparator.comparingDouble(Amenity::getPrice));
    }

    public List<Amenity> getAmenitiesSortedByName() {
        return amenityCol.getSortedAmenities(Comparator.comparing(Amenity::getName));
    }
}