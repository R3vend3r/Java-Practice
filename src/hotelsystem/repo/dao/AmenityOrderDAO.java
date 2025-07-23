package hotelsystem.repo.dao;

import hotelsystem.model.AmenityOrder;
import hotelsystem.Utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class AmenityOrderDAO extends HibernateBaseDAO<AmenityOrder, String> {
    private static final Logger logger = LoggerFactory.getLogger(AmenityOrderDAO.class);

    public AmenityOrderDAO() {
        super(AmenityOrder.class);
    }

    public List<AmenityOrder> findByClientId(String clientId) {
        try (Session session = HibernateUtils.getSession()) {
            Query<AmenityOrder> query = session.createQuery(
                    "FROM AmenityOrder ao WHERE ao.client.id = :clientId", AmenityOrder.class);
            query.setParameter("clientId", clientId);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding amenity orders by client id", e);
            throw new RuntimeException("Failed to find amenity orders by client id", e);
        }
    }

    public List<AmenityOrder> findByAmenityId(String amenityId) {
        try (Session session = HibernateUtils.getSession()) {
            Query<AmenityOrder> query = session.createQuery(
                    "FROM AmenityOrder ao WHERE ao.amenity.id = :amenityId", AmenityOrder.class);
            query.setParameter("amenityId", amenityId);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding amenity orders by amenity id", e);
            throw new RuntimeException("Failed to find amenity orders by amenity id", e);
        }
    }

    public List<AmenityOrder> findByDateRange(Date startDate, Date endDate) {
        try (Session session = HibernateUtils.getSession()) {
            Query<AmenityOrder> query = session.createQuery(
                    "FROM AmenityOrder ao WHERE ao.serviceDate BETWEEN :startDate AND :endDate",
                    AmenityOrder.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding amenity orders by date range", e);
            throw new RuntimeException("Failed to find amenity orders by date range", e);
        }
    }
}