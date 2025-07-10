package service;

import enums.SortType;
import interfaceClass.IOrderRepository;
import interfaceClass.*;
import model.*;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderService {
    private final IOrderRepository orderRepository;

    public OrderService(IOrderRepository orderRepository) {
        this.orderRepository = Objects.requireNonNull(orderRepository, "OrderRepository cannot be null");
    }

    public void createRoomBooking(Client client, Room room,
                                         Date checkInDate, Date checkOutDate) {
         orderRepository.createRoomBooking(client, room, checkInDate, checkOutDate);
    }

    public AmenityOrder addAmenityToBooking(int roomNumber, Amenity amenity, Date serviceDate) {
        RoomBooking booking = orderRepository.findActiveBookingByRoom(roomNumber)
                .orElseThrow(() -> new IllegalArgumentException("No active booking for room " + roomNumber));

        AmenityOrder order = orderRepository.addAmenityOrder(booking.getClient(), amenity, serviceDate);
        booking.addService(order);
        return order;
    }

    public void completeRoomBooking(int roomNumber, Date checkOutDate) {
        orderRepository.completeRoomBooking(roomNumber, checkOutDate);
    }

    public double calculatingTotalIncome(){
        return orderRepository.calculatingTotalIncome();
    }

    public List<RoomBooking> getActiveBookingsSorted(SortType sortType) {
        return orderRepository.getSortedBookings(sortType);
    }

    public double calculateAmenityCost(int roomNumber) {
        return orderRepository.calculateAmenityCost(roomNumber);
    }

    public List<RoomBooking> getCompletedBookings() {
        return orderRepository.getCompletedBookings();
    }

    public List<AmenityOrder> getAmenityOrdersSorted(SortType sortType) {
        return orderRepository.getSortedAmenityOrders(sortType);
    }

    public List<RoomBooking> getLastThreeBookingsForRoom(int roomNumber) {
        return orderRepository.getLastThreeBookingsForRoom(roomNumber);
    }

//    public Optional<IRoomBooking> findActiveBookingByRoom(int roomNumber) {
//        return orderRepository.findActiveBookingByRoom(roomNumber);
//    }

//    public double calculateTotalRevenue() {
//        double activeBookingsRevenue = orderRepository.getActiveBookings().stream()
//                .mapToDouble(RoomBooking::getTotalPrice)
//                .sum();
//
//        double completedBookingsRevenue = orderRepository.getCompletedBookings().stream()
//                .mapToDouble(RoomBooking::getTotalPrice)
//                .sum();
//
//        double amenityRevenue = orderRepository.getAmenityOrders().stream()
//                .mapToDouble(AmenityOrder::getTotalPrice)
//                .sum();
//
//        return activeBookingsRevenue + completedBookingsRevenue + amenityRevenue;
//    }

//    public List<IAmenityOrder> getAmenitiesForClient(String clientId) {
//        Objects.requireNonNull(clientId, "Client ID cannot be null");
//
//        return orderRepository.getAmenityOrders().stream()
//                .filter(order -> clientId.equals(order.getClient().getClientId()))
//                .collect(Collectors.toList());
//    }
}