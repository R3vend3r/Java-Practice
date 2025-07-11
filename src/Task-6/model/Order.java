package model;

import java.util.Date;
import java.util.Objects;

public abstract class Order {
    private String id;
    private Client client;
    private Date creationDate;
    private Date availableDate;
    private double totalPrice;

    protected Order(String id, Client client, Date creationDate, Date availableDate) {
        setId(id);
        setClient(client);
        setCreationDate(creationDate);
        setAvailableDate(availableDate);
    }

    protected Order(Client client, Date creationDate, Date availableDate) {
        this(generateId(), client, creationDate, availableDate);
    }

    static String generateId() {
        return "OR-" + System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "Order ID cannot be null");
        if (id.isBlank()) {
            throw new IllegalArgumentException("Order ID cannot be blank");
        }
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = Objects.requireNonNull(client, "Client cannot be null");
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = Objects.requireNonNull(creationDate, "Creation date cannot be null");
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = Objects.requireNonNull(availableDate, "Available date cannot be null");
        if (availableDate.before(creationDate)) {
            throw new IllegalArgumentException("Available date cannot be before creation date");
        }
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        if (totalPrice < 0) {
            throw new IllegalArgumentException("Total price cannot be negative");
        }
        this.totalPrice = totalPrice;
    }
}