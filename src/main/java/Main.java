
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.HibernateUtil;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final UserDAO userDao = new UserDaoImpl();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            logger.info("Приложение запушено");
            boolean running = true;

            while (running) {
                printMenu();
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createUser();
                        break;
                    case 2:
                        getUserById();
                        break;
                    case 3:
                        getAllUsers();
                        break;
                    case 4:
                        updateUser();
                        break;
                    case 5:
                        deleteUser();
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Неправильный выбор, попробуйте снова.");
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred: ", e);
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            HibernateUtil.shutdown();
            scanner.close();
            logger.info("Application stopped");
        }
    }

    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Создать нового пользователя");
        System.out.println("2. Получить пользователя по ID");
        System.out.println("3. Получить всех пользователей");
        System.out.println("4. Обновить пользователя");
        System.out.println("5. Удалить пользователя");
        System.out.println("0. Выход");
        System.out.print("Введите число: ");
    }

    private static void createUser() {
        System.out.println("\nСоздать нового пользователя");
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();

        System.out.print("Введите email: ");
        String email = scanner.nextLine();

        System.out.print("Введите возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        User user = new User(name, email, age);
        userDao.save(user);
        System.out.println("Пользователь создан успешно: " + user);
        logger.info("Создан пользователь: {}", user);
    }

    private static void getUserById() {
        System.out.println("\nПолучить пользователя по ID");
        System.out.print("Введите  ID пользователя: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        User user = userDao.findById(id);
        if (user != null) {
            System.out.println("Пользователь найден: " + user);
        } else {
            System.out.println("Пользователь с  ID не найден: " + id);
        }
    }

    private static void getAllUsers() {
        System.out.println("\nВсе пользователи:");
        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            System.out.println("Пользователей не найдено.");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void updateUser() {
        System.out.println("\nОбновить пользователя");
        System.out.print("Введите ID Пользователя: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        User user = userDao.findById(id);
        if (user == null) {
            System.out.println("Пользователь с  ID не найден: " + id);
            return;
        }

        System.out.println("Текущие данные: " + user);

        System.out.print("Ведите имя (пропустите что бы оставить текущее): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            user.setName(name);
        }

        System.out.print("Введите новый email (пропустите что бы оставить текущее): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            user.setEmail(email);
        }

        System.out.print("Введите новый возраст (0 что бы оставить текущий): ");
        int age = scanner.nextInt();
        scanner.nextLine();
        if (age > 0) {
            user.setAge(age);
        }

        userDao.update(user);
        System.out.println("Пользователь обновлен успешно: " + user);
        logger.info("Пользователь обновлен: {}", user);
    }

    private static void deleteUser() {
        System.out.println("\nУдаление пользователя");
        System.out.print("Введите ID пользователя: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        User user = userDao.findById(id);
        if (user == null) {
            System.out.println("ППользователь с таким ID не найден: " + id);
            return;
        }

        userDao.delete(user);
        System.out.println("Пользователь с ID успешно удален: " + id);
        logger.info("Пользователь с ID удален: {}", id);
    }
}