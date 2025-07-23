package hotelsystem.repo.dao;

import hotelsystem.Utils.HibernateUtils;
import hotelsystem.model.Room;
import hotelsystem.enums.RoomCondition;
import hotelsystem.enums.RoomType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class RoomDAO extends HibernateBaseDAO<Room, Integer> {
    private static final Logger logger = LoggerFactory.getLogger(RoomDAO.class);

    public RoomDAO() {
        super(Room.class);
    }

    public List<Room> findAvailableRooms() {
        try (Session session = HibernateUtils.getSession()) {
            Query<Room> query = session.createQuery(
                    "FROM Room r WHERE r.isAvailable = true", Room.class);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding available rooms", e);
            throw new RuntimeException("Failed to find available rooms", e);
        }
    }


    public List<Room> findAvailableByDate(Date date) {
        try (Session session = HibernateUtils.getSession()) {
            Query<Room> query = session.createQuery(
                    "FROM Room r WHERE r.isAvailable = true OR " +
                            "(r.availableDate IS NOT NULL AND r.availableDate <= :date)", Room.class);
            query.setParameter("date", date);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding available rooms by date", e);
            throw new RuntimeException("Failed to find available rooms by date", e);
        }
    }

    public void delete(Integer roomNumber) {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSession()) {
            transaction = session.beginTransaction();

            // 1. Найдем комнату
            Room room = session.get(Room.class, roomNumber);
            if (room != null) {
                // 2. Очистим связь с клиентом
                if (room.getClient() != null) {
                    room.getClient().setRoom(null);
                    session.merge(room.getClient());
                }

                // 3. Удалим комнату
                session.delete(room);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Failed to delete room", e);
        }
    }
}