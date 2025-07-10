package service;

import enums.SortType;
import model.*;

import java.util.Date;
import java.util.List;

public class OrderService {
    private final OrderCol orderCol;

    public OrderService() {
        this.orderCol = new OrderCol();
    }

    public void createRoomOrder(Client client, Room room, Date checkInDate, Date checkOut) {
        orderCol.checkIn(client, room, checkInDate, checkOut);
    }

    public void createServiceOrder(Client client, Amenity service, Date date) {
        orderCol.addService(client, service, date);
    }

    public void completeRoomOrder(int roomNumber, Date checkOutDate) {
        orderCol.checkOut(roomNumber, checkOutDate);
    }

    // Универсальный метод для сортировки
    public List<Order> getSortedOrders(SortType sortType) {
        return orderCol.getSortedOrders(sortType);
    }
    public List<Order>  getSortedAmenities(SortType sortType){
        return orderCol.getSortedAmenities(sortType);
    }

    // Специальные методы без изменений
    public List<Order> getLastThreeRoomClients(int roomNumber) {
        return orderCol.getLastThreeClientsForRoom(roomNumber);
    }

    public int countActiveClients() {
        return orderCol.countActiveClients();
    }
}