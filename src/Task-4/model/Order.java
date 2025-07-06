package model;

import java.util.Date;

public class Order {
    private int orderId;
    private Room room;
    private Client client;
    private Amenity service;
    private double price;
    private  Date dateIn;
    private  Date endDate;

    public Order(Client client, Room room, Date dateIn, Date dateOut, double price) {
        this.room = room;
        this.price = price;
        this.dateIn = dateIn;
        this.endDate = dateOut;
        this.client = client;
    }

    public Order(Client client, Amenity service,Date date, double price) {
        this.client = client;
        this.service = service;
        this.price = price;
        this.endDate = date;
    }

    public Client getClient() {
        return client;
    }

    public Room getRoom() {
        return room;
    }


    public Amenity getService() {
        return service;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getDateOut() {
        return endDate;
    }

    public Order getInfoById(int id){
        return null;
    }

}
