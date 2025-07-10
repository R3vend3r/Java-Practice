package model;

import java.util.Date;

public abstract class Order{
    private final int id;
    private static int nextId = 0;
    private final Client client;
    private final Date creationDate;
    private final Date availableDate;
    private double totalPrice;

    protected Order(Client client, Date creationDate, Date availableDate) {
        this.id = nextId++;
        this.client = client;
        this.creationDate = creationDate;
        this.availableDate = availableDate;
    }

    public int getId() { return id; }
    public Client getClient() { return client; }
    public Date getCreationDate() { return creationDate; }
    public Date getAvailableDate() { return availableDate; }
    public double getTotalPrice() { return totalPrice; }

    protected void setTotalPrice(double price) { this.totalPrice = price; }
}