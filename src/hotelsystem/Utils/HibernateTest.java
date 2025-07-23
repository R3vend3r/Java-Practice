package hotelsystem.Utils;

import hotelsystem.enums.RoomCondition;
import hotelsystem.enums.RoomType;
import hotelsystem.model.Client;
import hotelsystem.model.Room;
import hotelsystem.model.RoomBooking;
import hotelsystem.repo.dao.ClientDAO;
import hotelsystem.repo.dao.RoomBookingDAO;
import hotelsystem.repo.dao.RoomDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Calendar;
import java.util.Date;

public class HibernateTest {
    public static void main(String[] args) {
        try {
            clearDatabase();
            RoomDAO roomDAO = new RoomDAO();
            Room room = new Room(102, RoomType.STANDARD, 2500.0, 2, RoomCondition.READY, 3);
            roomDAO.create(room);

            ClientDAO clientDAO = new ClientDAO();
            Client client = new Client("John", "Doe");
            clientDAO.create(client);

            RoomBookingDAO bookingDAO = new RoomBookingDAO();
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DATE, 3);
            Date newDate = c.getTime();
            RoomBooking booking = new RoomBooking(client, room, 5000.0, new Date(), newDate);
            bookingDAO.create(booking);

            System.out.println("Available rooms: " + roomDAO.findAvailableRooms());
            System.out.println("Client bookings: " + bookingDAO.findByClientId(client.getId()));

        } finally {
            HibernateUtils.shutdown();
        }
    }

    private static void clearDatabase() {
        try (Session session = HibernateUtils.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM RoomBooking").executeUpdate();
            session.createMutationQuery("DELETE FROM AmenityOrder").executeUpdate();
            session.createMutationQuery("DELETE FROM Client").executeUpdate();
            session.createMutationQuery("DELETE FROM Room").executeUpdate();
            transaction.commit();
        }
    }
}