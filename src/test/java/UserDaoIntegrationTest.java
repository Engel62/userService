import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import util.HibernateUtil;


import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserDaoIntegrationTest {
    @Container
    public static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private static UserDAO userDAO;

    @BeforeAll
    static void setup() {
        System.setProperty("db.url",postgres.getJdbcUrl());
        System.setProperty("db.username", postgres.getUsername());
        System.setProperty("db.password", postgres.getPassword());
        userDAO = new UserDaoImpl();

    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @BeforeEach
    void cleanDatabase() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        }
        }

    @Test
    public void shouldUpdateUser() {
        User user = new User("Original", "original@email.com", 25);
        userDAO.save(user);

        user.setName("Updated");
        user.setEmail("updated@email.com");
        user.setAge(30);
        userDAO.update(user);

        User updated = userDAO.findById(user.getId());

        assertEquals("Updated", updated.getName());
        assertEquals("updated@email.com", updated.getEmail());
        assertEquals(30, updated.getAge());
    }

    @Test
    public void shouldDeleteUser() {
        User user = new User("To Delete", "delete@email.com", 40);
        userDAO.save(user);

        userDAO.delete(user);

        assertNull(userDAO.findById(user.getId()));
    }

    @Test
    public void shouldFindAllUsers() {
        userDAO.save(new User("User1", "user1@email.com", 20));
        userDAO.save(new User("User2", "user2@email.com", 25));

        List<User> users = userDAO.findAll();

        assertEquals(2, users.size());
    }
    }

