package hotelsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "amenities")
public class Amenity implements Serializable {
    @Id
    @Column(name = "id")
    private String id;
    @Getter
    @Column(name = "name", nullable = false)
    private String name;
    @Getter
    @Column(name = "price", nullable = false)
    private double price;

    public Amenity() {
    }

    public Amenity(String id, String name, double price) {
        setId(id);
        setName(name);
        setPrice(price);
    }

    public Amenity(String name, double price) {
        this(generateId(), name, price);
    }

    public String getId() {
        return id;
    }

    private static String generateId() {
        return "AM-" + System.currentTimeMillis();
    }

    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "Amenity ID cannot be null");
        if (id.isBlank()) {
            throw new IllegalArgumentException("Amenity ID cannot be blank");
        }
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Amenity name cannot be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Amenity name cannot be blank");
        }
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Amenity[id=%s, name=%s, price=%.2f]", id, name, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amenity amenity = (Amenity) o;
        return id.equals(amenity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}