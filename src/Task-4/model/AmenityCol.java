package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AmenityCol {
    private final List<Amenity> amenities;

    public AmenityCol() {
        amenities = new ArrayList<>();
    }

    public void addAmenity(Amenity amenity) {
        if (amenity == null) {
            System.out.println("Ошибка: услуга не может быть null");
            return;
        }
        if (amenities.stream().anyMatch(a -> a.getName().equals(amenity.getName()))) {
            System.out.println("Услуга '" + amenity.getName() + "' уже существует");
            return;
        }
        amenities.add(amenity);
        System.out.println("Услуга '" + amenity.getName() + "' добавлена");
    }

    public boolean containsAmenity(Amenity amenity) {
        return amenity != null && amenities.stream()
                .anyMatch(a -> a.getName().equals(amenity.getName()));
    }

    public List<Amenity> getAllAmenities() {
        System.out.println("Текущие услуги:");
        amenities.forEach(amenity ->
                System.out.printf("- %s (%.2f руб.)%n", amenity.getName(), amenity.getPrice()));

        return new ArrayList<>(amenities);
    }

    public List<Amenity> getSortedAmenities(Comparator<Amenity> comparator) {
        return amenities.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

}