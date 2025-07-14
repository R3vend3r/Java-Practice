package model;

import comparator.AmenityComparator.*;
import comparator.OrderCorparator.*;
import interfaceClass.*;
import enums.SortType;

import java.util.*;
import java.util.stream.Collectors;

public class OrderRepository implements IOrderRepository {
    private final List<RoomBooking> activeBookings;
    private final List<RoomBooking> completedBookings;
    private final List<AmenityOrder> amenityOrders;

    public OrderRepository() {
        this.activeBookings = new ArrayList<>();
        this.completedBookings = new ArrayList<>();
        this.amenityOrders = new ArrayList<>();
    }

    @Override
    public void createRoomBooking(Client client, Room room,
                                          Date checkInDate, Date checkOutDate) {
        Objects.requireNonNull(client, "Client cannot be null");
        Objects.requireNonNull(room, "Room cannot be null");
        Objects.requireNonNull(checkInDate, "CheckIn date cannot be null");
        Objects.requireNonNull(checkOutDate, "CheckOut date cannot be null");

        if (checkOutDate.before(checkInDate)) {
            throw new IllegalArgumentException("CheckOut date must be after CheckIn date");
        }
        double price = room.getPriceForDay();
        double totalPrice = calculateStayCost(price, checkInDate, checkOutDate);
        RoomBooking booking = new RoomBooking(client, room, totalPrice, new Date(), checkOutDate);
        activeBookings.add(booking);
        completedBookings.add(booking);
    }


    @Override
    public double calculateStayCost(double priceForDay, Date checkInDate, Date endDate) {
        Objects.requireNonNull(endDate, "End date cannot be null");
        return priceForDay * calculateDaysBetween(checkInDate, endDate);
    }

    private double calculateDaysBetween(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        return Math.ceil((double) diff / (1000 * 60 * 60 * 24));
    }

    @Override
    public AmenityOrder addAmenityOrder(Client client, Amenity amenity, Date serviceDate) {
        Objects.requireNonNull(client, "Client cannot be null");
        Objects.requireNonNull(amenity, "Amenity cannot be null");
        Objects.requireNonNull(serviceDate, "Service date cannot be null");

        AmenityOrder order = new AmenityOrder(client, amenity, serviceDate);
        amenityOrders.add(order);
        return order;
    }

    @Override
    public void completeRoomBooking(int roomNumber, Date checkOutDate) throws IllegalArgumentException {
        Objects.requireNonNull(checkOutDate, "CheckOut date cannot be null");

        RoomBooking booking = activeBookings.stream()
                .filter(b -> b.getRoom().getNumberRoom() == roomNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Room " + roomNumber + " is not occupied"));

        booking.setCheckOutDate(checkOutDate);
        activeBookings.remove(booking);
    }

    @Override
    public List<RoomBooking> getActiveBookings() {
        return Collections.unmodifiableList(activeBookings);
    }

    @Override
    public List<AmenityOrder> getAmenityOrders() {
        return Collections.unmodifiableList(amenityOrders);
    }

    @Override
    public List<RoomBooking> getCompletedBookings() {
        return Collections.unmodifiableList(completedBookings);
    }

    @Override
    public double calculatingTotalIncome() {
        double bookingsIncome = completedBookings.stream()
                .mapToDouble(RoomBooking::getTotalPrice)
                .sum();

        double amenitiesIncome = amenityOrders.stream()
                .mapToDouble(order -> order.getAmenity().getPrice())
                .sum();

        return bookingsIncome + amenitiesIncome;
    }

    @Override
    public double calculateAmenityCost(int roomNumber) {
        return activeBookings.stream()
                .filter(booking -> booking.getRoom().getNumberRoom() == roomNumber)
                .flatMap(booking -> booking.getServices().stream())
                .mapToDouble(order -> order.getAmenity().getPrice())
                .sum();
    }

    @Override
    public List<RoomBooking> getSortedBookings(SortType sortType) throws IllegalArgumentException{
        Objects.requireNonNull(sortType, "Sort type cannot be null");

        Comparator<Order> comparator = switch (sortType) {
            case ALPHABET -> new AlphabetComparator();
            case DATE_END -> new DateComparator();
            case NONE -> (a, b) -> 0;
            default -> throw new IllegalArgumentException("Unsupported sort type for bookings: " + sortType);
        };

        return activeBookings.stream()
                .sorted(comparator.reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<AmenityOrder> getSortedAmenityOrders(SortType sortType) throws IllegalArgumentException{
        Objects.requireNonNull(sortType, "Sort type cannot be null");

        Comparator<AmenityOrder> comparator = switch (sortType) {
            case DATE_END -> new DateAmenComparator();
            case PRICE -> new PriceAmenComparator();
            case NONE -> (a, b) -> 0;
            default -> throw new IllegalArgumentException("Unsupported sort type for amenities: " + sortType);
        };

        return amenityOrders.stream()
                .sorted(comparator.reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomBooking> getLastThreeBookingsForRoom(int roomNumber) {
        return completedBookings.stream()
                .filter(booking -> booking.getRoom().getNumberRoom() == roomNumber)
                .sorted(Comparator.comparing(RoomBooking::getCheckOutDate).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoomBooking> findActiveBookingByRoom(int roomNumber) {
        return activeBookings.stream()
                .filter(booking -> booking.getRoom().getNumberRoom() == roomNumber)
                .findFirst();
    }
    @Override
    public void clearAll() {
        amenityOrders.clear();
        completedBookings.clear();
        activeBookings.clear();
    }
}