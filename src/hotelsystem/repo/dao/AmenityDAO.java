package hotelsystem.repo.dao;

import hotelsystem.Utils.HibernateUtils;
import hotelsystem.model.Amenity;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class AmenityDAO extends HibernateBaseDAO<Amenity, String> {
    private static final Logger logger = LoggerFactory.getLogger(AmenityDAO.class);

    public AmenityDAO() {
        super(Amenity.class);
    }

    public Optional<Amenity> findByName(String name) {
        try (Session session = HibernateUtils.getSession()) {
            Query<Amenity> query = session.createQuery(
                    "FROM Amenity a WHERE a.name = :name", Amenity.class);
            query.setParameter("name", name);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            logger.error("Error finding amenity by name", e);
            throw new RuntimeException("Failed to find amenity by name", e);
        }
    }

    public List<Amenity> findByPriceRange(double minPrice, double maxPrice) {
        try (Session session = HibernateUtils.getSession()) {
            Query<Amenity> query = session.createQuery(
                    "FROM Amenity a WHERE a.price BETWEEN :minPrice AND :maxPrice", Amenity.class);
            query.setParameter("minPrice", minPrice);
            query.setParameter("maxPrice", maxPrice);
            return query.getResultList();
        } catch (Exception e) {
            logger.error("Error finding amenities by price range", e);
            throw new RuntimeException("Failed to find amenities by price range", e);
        }
    }
}