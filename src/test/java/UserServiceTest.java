import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserDAO userDAO;
    @InjectMocks
    private UserService userService;
    @Test
    void shouldCreateUser() {
        User newUser = new User("Test", "test@email.com", 25);
        when(userDAO.save(any(User.class))).thenReturn(newUser);

        User created = userService.createUser("Test", "test@email.com", 25);

        assertNotNull(created);
        assertEquals("Test", created.getName());
        assertEquals("test@email.com", created.getEmail());
        assertEquals(25, created.getAge());
    }

    @Test
    void shouldGetUserById() {
        User user = new User("Existing", "existing@email.com", 30);
        when(userDAO.findById(1L)).thenReturn(user);

        User found = userService.getUserById(1L);

        assertNotNull(found);
        assertEquals("Existing", found.getName());
        verify(userDAO).findById(1L);
    }

    @Test
    void shouldGetAllUsers() {
        List<User> users = List.of(
                new User("User1", "user1@email.com", 20),
                new User("User2", "user2@email.com", 25)
        );
        when(userDAO.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(2, result.size());
        verify(userDAO).findAll();
    }

    @Test
    void shouldUpdateUser() {
        User existingUser = new User("Old", "old@email.com", 30);
        when(userDAO.findById(1L)).thenReturn(existingUser);

        User updated = userService.updateUser(1L, "New", "new@email.com", 35);

        assertEquals("New", updated.getName());
        assertEquals("new@email.com", updated.getEmail());
        assertEquals(35, updated.getAge());
        verify(userDAO).update(existingUser);
    }

    @Test
    void shouldThrowWhenUpdateNonExistingUser() {
        when(userDAO.findById(999L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser(999L, "Name", "email@test.com", 25);
        });
    }

    @Test
    void shouldDeleteUser() {
        User user = new User("ToDelete", "delete@test.com", 40);
        when(userDAO.findById(1L)).thenReturn(user);

        userService.deleteUser(1L);

    verify(userDAO).delete(user);
    }

    @Test
    void shouldThrowWhenDeleteNonExistingUser() {
        when(userDAO.findById(999L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.deleteUser(999L);
        });
    }
}
