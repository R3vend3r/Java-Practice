package hotelsystem.repo.dao;

import hotelsystem.Exception.DatabaseException;
import hotelsystem.model.RoomBooking;
import hotelsystem.Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class RoomBookingDAO extends HibernateBaseDAO<RoomBooking, String> {
    private static final Logger logger = LoggerFactory.getLogger(RoomBookingDAO.class);

    public RoomBookingDAO() {
        super(RoomBooking.class);
    }

    @Override
    public void create(RoomBooking booking) throws DatabaseException {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtils.getSession();
            transaction = session.beginTransaction();

            // Проверяем и сохраняем клиента, если он новый
            if (booking.getClient() != null && booking.getClient().getId() == null) {
                session.persist(booking.getClient());
            }

            // Проверяем и сохраняем комнату, если она новая
            if (booking.getRoom() != null && booking.getRoom().getNumberRoom() == null) {
                session.persist(booking.getRoom());
            }

            // Сохраняем само бронирование
            session.persist(booking);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackEx) {
                    logger.error("Failed to rollback transaction", rollbackEx);
                }
            }
            logger.error("Error creating booking", e);
            throw new DatabaseException("Failed to create booking", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public List<RoomBooking> findActiveBookings() {
        try (Session session = HibernateUtils.getSession()) {
            Query<RoomBooking> query = session.createQuery(
                    "FROM RoomBooking rb WHERE rb.checkOutDate > :currentDate", RoomBooking.class);
            query.setParameter("currentDate", new Date());
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding active bookings", e);
            throw new RuntimeException("Failed to find active bookings", e);
        }
    }

    public List<RoomBooking> findCompletedBookings() {
        try (Session session = HibernateUtils.getSession()) {
            Query<RoomBooking> query = session.createQuery(
                    "FROM RoomBooking rb WHERE rb.checkOutDate <= :currentDate", RoomBooking.class);
            query.setParameter("currentDate", new Date());
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding completed bookings", e);
            throw new RuntimeException("Failed to find completed bookings", e);
        }
    }

    public List<RoomBooking> findByRoomNumber(int roomNumber) {
        try (Session session = HibernateUtils.getSession()) {
            Query<RoomBooking> query = session.createQuery(
                    "FROM RoomBooking rb WHERE rb.room.numberRoom = :roomNumber ORDER BY rb.checkOutDate DESC",
                    RoomBooking.class);
            query.setParameter("roomNumber", roomNumber);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding bookings by room number", e);
            throw new RuntimeException("Failed to find bookings by room number", e);
        }
    }

    public List<RoomBooking> findByClientId(String clientId) {
        try (Session session = HibernateUtils.getSession()) {
            Query<RoomBooking> query = session.createQuery(
                    "FROM RoomBooking rb WHERE rb.client.id = :clientId", RoomBooking.class);
            query.setParameter("clientId", clientId);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding bookings by client id", e);
            throw new RuntimeException("Failed to find bookings by client id", e);
        }
    }
}