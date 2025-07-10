package model;

import comparator.AmenityComparator.DateAmenComparator;
import comparator.AmenityComparator.PriceAmenComparator;
import comparator.OrderCorparator.AlphabetComparator;
import comparator.OrderCorparator.DateComparator;

import enums.SortType;
import java.util.*;
import java.util.stream.Collectors;

public class OrderCol {
    private final List<Order> activeOrders;
    private final List<Order> completedOrders;
    private final List<Order> amenityOrders;

    public OrderCol() {
        activeOrders = new ArrayList<>();
        completedOrders = new ArrayList<>();
        amenityOrders = new ArrayList<>();
    }

    public void checkIn(Client client, Room room, Date checkInDate, Date checkOutDate) {
        Order order = new Order(client, room, checkInDate, checkOutDate, room.getPriceForDay());
        activeOrders.add(order);
        completedOrders.add(order);
    }

    public void addService(Client client, Amenity service, Date date) {
        Order order = new Order(client, service, date, service.getPrice());
        amenityOrders.add(order);
    }

    public void checkOut(int roomId, Date checkOutDate) {
        Order order = activeOrders.stream()
                .filter(o -> o.getRoom() != null && o.getRoom().getNumberRoom() == roomId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Номер не занят!"));
        order.setEndDate(checkOutDate);
        activeOrders.remove(order);
    }

    public List<Order> getSortedOrders(SortType sortType) {
        Comparator<Order> comparator = getOrderComparator(sortType);
        return activeOrders.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Order> getSortedAmenities(SortType sortType) {
        Comparator<Order> comparator = getAmenityComparator(sortType);
        return amenityOrders.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private Comparator<Order> getOrderComparator(SortType sortType) {
        return switch (sortType) {
            case ALPHABET -> new AlphabetComparator();
            case DATE_END -> new DateComparator();
            case NONE -> (a,b) -> 0;
            default -> throw new IllegalArgumentException("Неподдерживаемый тип сортировки для заказов: " + sortType);
        };
    }

    private Comparator<Order> getAmenityComparator(SortType sortType) {
        return switch (sortType) {
            case DATE_END -> new DateAmenComparator();
            case PRICE -> new PriceAmenComparator();
            case NONE -> (a,b) -> 0;
            default -> throw new IllegalArgumentException("Неподдерживаемый тип сортировки для услуг: " + sortType);
        };
    }

    public List<Order> getLastThreeClientsForRoom(int roomNumber) {
        return completedOrders.stream()
                .filter(o -> o.getRoom() != null && o.getRoom().getNumberRoom() == roomNumber)
                .sorted(Comparator.comparing(Order::getDateOut).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Order> getAvailableRoomsByDate(Date date) {
        return activeOrders.stream()
                .filter(o -> o.getDateOut() != null && o.getDateOut().equals(date))
                .collect(Collectors.toList());
    }

    // Остальные методы без изменений
    public List<Order> getClientHistory(String clientId) {
        return completedOrders.stream()
                .filter(o -> o.getClient().getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    public List<Order> getRoomHistory(int roomId) {
        return completedOrders.stream()
                .filter(o -> o.getRoom() != null && o.getRoom().getNumberRoom() == roomId)
                .collect(Collectors.toList());
    }

    public int countActiveClients() {
        return (int) activeOrders.stream()
                .map(o -> o.getClient().getClientId())
                .distinct()
                .count();
    }
}