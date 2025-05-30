package util;

import org.hibernate.SessionFactory;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();

            // Переопределяем настройки из системных свойств
            if (System.getProperty("db.url") != null) {
                configuration.setProperty("hibernate.connection.url", System.getProperty("db.url"));
                configuration.setProperty("hibernate.connection.username", System.getProperty("db.username"));
                configuration.setProperty("hibernate.connection.password", System.getProperty("db.password"));
            }

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            return configuration.buildSessionFactory(registry);
        } catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}


