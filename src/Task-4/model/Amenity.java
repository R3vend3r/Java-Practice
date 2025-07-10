package model;

import interfaceClass.*;
import java.util.Objects;

public class Amenity{
    private String name;
    private double price;

    public Amenity(String name, double price) {
        setName(name);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Amenity name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Amenity name cannot be blank");
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    public String toString() {
        return String.format("Amenity[name=%s, price=%.2f]", name, price);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amenity amenity = (Amenity) o;
        return name.equals(amenity.name);
    }

    public int hashCode() {
        return Objects.hash(name);
    }
}