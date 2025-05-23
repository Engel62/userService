import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class UserDAO {
    private final SessionFactory sessionFactory;

    public UserDAO() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public void save(User user) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        }
        }

    public User findById(Long id) {
        try (Session session = this.sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> findAll() {
        try (Session session = this.sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(User user) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
        if (transaction!= null) transaction.rollback();
        e.printStackTrace();
    }
    }

    public void delete(User user) {
        Transaction transaction = null;
        try (Session session = this.sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
    }

