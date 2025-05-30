import java.util.List;

public class UserService {

    private final UserDAO userDao;

    public UserService(UserDAO userDao) {
        this.userDao = userDao;
    }

    public User createUser(String name, String email, int age) {
        User user = new User(name, email, age);
        userDao.save(user);
        return user;
    }

    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public User updateUser(Long id, String name, String email, int age) {
        User user = userDao.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }

        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        userDao.update(user);
        return user;
    }

    public void deleteUser(Long id) {
        User user = userDao.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        userDao.delete(user);
    }
}
