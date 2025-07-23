package hotelsystem.repo.dao;

import hotelsystem.model.Client;
import hotelsystem.Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ClientDAO extends HibernateBaseDAO<Client, String> {
    private static final Logger logger = LoggerFactory.getLogger(ClientDAO.class);

    public ClientDAO() {
        super(Client.class);
    }

    public List<Client> findByRoomNumber(int roomNumber) {
        try (Session session = HibernateUtils.getSession()) {
            Query<Client> query = session.createQuery(
                    "FROM Client c WHERE c.room.numberRoom = :roomNumber", Client.class);
            query.setParameter("roomNumber", roomNumber);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding clients by room number", e);
            throw new RuntimeException("Failed to find clients by room number", e);
        }
    }

    public Optional<Client> findByRoom(int roomNumber) {
        try (Session session = HibernateUtils.getSession()) {
            Query<Client> query = session.createQuery(
                    "FROM Client c WHERE c.room.numberRoom = :roomNumber", Client.class);
            query.setParameter("roomNumber", roomNumber);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            logger.error("Error finding client by room", e);
            throw new RuntimeException("Failed to find client by room", e);
        }
    }
}