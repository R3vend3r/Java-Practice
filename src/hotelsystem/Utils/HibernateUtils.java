package hotelsystem.Utils;

import hotelsystem.model.*;
import jakarta.persistence.Embeddable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

@Embeddable
public class HibernateUtils {
    private static final SessionFactory sessionFactory;

    static {
        try {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure("file:src/resources/hibernate.cfg.xml")
                    .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
                    .build();

            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(Room.class)
                    .addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Amenity.class)
                    .addAnnotatedClass(AmenityOrder.class)
                    .addAnnotatedClass(RoomBooking.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }

    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}