import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;


public interface UserDAO {
    User save(User user);
    User findById(Long id);
    List<User> findAll();
    void update(User user);
    void delete(User user);
}