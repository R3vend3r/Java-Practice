package model;

import java.util.Date;

public class Order {
    private int getId;
    private static int id = 0;
    private Room room;
    private Client client;
    private Amenity service;
    private double price;
    private  Date startDate;
    private  Date endDate;

    public Order(Client client, Room room, Date startDate, Date dateOut, double price) {
        this.getId = id++;
        this.room = room;
        this.price = price;
        this.startDate = startDate;
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
