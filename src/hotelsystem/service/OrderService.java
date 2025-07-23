package hotelsystem.service;

import hotelsystem.Exception.DatabaseException;
import hotelsystem.dependencies.annotation.Component;
import hotelsystem.dependencies.annotation.Inject;
import hotelsystem.enums.SortType;
import hotelsystem.interfaceClass.IClearable;
import hotelsystem.interfaceClass.IOrderRepository;
import hotelsystem.model.*;
import hotelsystem.repo.dao.AmenityOrderDAO;
import hotelsystem.repo.dao.RoomBookingDAO;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class OrderService implements IClearable {
    @Inject
    private IOrderRepository orderRepository;

    @Inject
    private RoomBookingDAO roomBookingDAO;

    @Inject
    private AmenityOrderDAO amenityOrderDAO;

    public void createRoomBooking(Client client, Room room, Date checkInDate, Date checkOutDate) {
        try {
            RoomBooking booking = new RoomBooking(
                    generateId(),
                    client,
                    room,
                    calculateStayCost(room.getPriceForDay(), checkInDate, checkOutDate),
                    checkInDate,
                    checkOutDate
            );

            roomBookingDAO.create(booking);
            orderRepository.addRoomBooking(booking);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create booking", e);
        }
    }

    public RoomBooking getActiveBookingByRoom(int roomNumber) {
        try {
            return roomBookingDAO.findActiveBookings().stream()
                    .filter(b -> b.getRoom().getNumberRoom() == roomNumber)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No active booking for room " + roomNumber));
        } catch (Exception e) {
            throw new RuntimeException("Failed to find active booking", e);
        }
    }

    public List<RoomBooking> getActiveBookingsSorted(SortType sortType) {
        try {
            List<RoomBooking> bookings = roomBookingDAO.findActiveBookings();
            return sortBookings(bookings, sortType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get active bookings", e);
        }
    }

    public List<RoomBooking> getCompletedBookings() {
        try {
            return roomBookingDAO.findCompletedBookings();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get completed bookings", e);
        }
    }

    public List<RoomBooking> getLastThreeBookingsForRoom(int roomNumber) {
        try {
            return roomBookingDAO.findByRoomNumber(roomNumber).stream()
                    .limit(3)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get last bookings", e);
        }
    }


    public void addAmenityToBooking(int roomNumber, Amenity amenity, Date serviceDate) {
        try {
            RoomBooking booking = getActiveBookingByRoom(roomNumber);

            AmenityOrder order = new AmenityOrder(
                    generateId(),
                    booking.getClient(),
                    amenity.getPrice(),
                    amenity,
                    serviceDate
            );

            amenityOrderDAO.create(order);
            orderRepository.addAmenityOrder(order);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add amenity to booking", e);
        }
    }

    public List<AmenityOrder> getAmenityOrdersSorted(SortType sortType) {
        try {
            List<AmenityOrder> orders = amenityOrderDAO.findAll();
            return sortAmenityOrders(orders, sortType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get amenity orders", e);
        }
    }

    public List<AmenityOrder> getAmenitiesForClient(String clientId) {
        try {
            return amenityOrderDAO.findByClientId(clientId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get amenities for client", e);
        }
    }

    public List<AmenityOrder> getAllAmenityOrders() {
        try {
            return amenityOrderDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all amenity orders", e);
        }
    }


    public double calculateAmenityCost(int roomNumber) {
        try {
            RoomBooking booking = getActiveBookingByRoom(roomNumber);
            return amenityOrderDAO.findByClientId(booking.getClient().getId()).stream()
                    .mapToDouble(AmenityOrder::getTotalPrice)
                    .sum();
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate amenity cost", e);
        }
    }

    public double calculatingTotalIncome() {
        try {
            double bookingsIncome = roomBookingDAO.findCompletedBookings().stream()
                    .mapToDouble(RoomBooking::getTotalPrice)
                    .sum();

            double amenitiesIncome = amenityOrderDAO.findAll().stream()
                    .mapToDouble(AmenityOrder::getTotalPrice)
                    .sum();

            return bookingsIncome + amenitiesIncome;
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate total income", e);
        }
    }

    @Override
    public void clear() {
        try {
            List<RoomBooking> roomBookings = roomBookingDAO.findAll();
            for (RoomBooking booking : roomBookings) {
                roomBookingDAO.delete(booking.getId());
            }

            List<AmenityOrder> amenityOrders = amenityOrderDAO.findAll();
            for (AmenityOrder order : amenityOrders) {
                amenityOrderDAO.delete(order.getId());
            }
            orderRepository.clearAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear orders", e);
        }
    }


    private String generateId() {
        return UUID.randomUUID().toString();
    }

    private double calculateStayCost(double pricePerDay, Date start, Date end) {
        long days = (end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24);
        return pricePerDay * Math.max(1, days);
    }

    private List<RoomBooking> sortBookings(List<RoomBooking> bookings, SortType sortType) {
        Comparator<RoomBooking> comparator = switch (sortType) {
            case DATE_END -> Comparator.comparing(RoomBooking::getCheckInDate);
            case PRICE -> Comparator.comparing(RoomBooking::getTotalPrice);
            default -> (a, b) -> 0;
        };
        return bookings.stream().sorted(comparator).collect(Collectors.toList());
    }

    private List<AmenityOrder> sortAmenityOrders(List<AmenityOrder> orders, SortType sortType) {
        Comparator<AmenityOrder> comparator = switch (sortType) {
            case DATE_END -> Comparator.comparing(AmenityOrder::getServiceDate);
            case PRICE -> Comparator.comparing(AmenityOrder::getTotalPrice);
            case ALPHABET -> Comparator.comparing(a -> a.getAmenity().getName());
            default -> (a, b) -> 0;
        };
        return orders.stream().sorted(comparator).collect(Collectors.toList());
    }
}