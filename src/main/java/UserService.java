import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class UserService {

        private final Scanner scanner;

        private final UserDAO userDAO;

        public UserService() {
            this.scanner = new Scanner(System.in);
            this.userDAO = new UserDAO();
        }

        public void start() {
            boolean run = true;
            while (run) {
                System.out.println("Choose an option");
                System.out.println("1. Create user\t2. Read user");
                System.out.println("3. Update user\t4. Delete user");
                System.out.println("5. List all users\t6. Exit");
                int option;
                try {
                    System.out.println("Enter an option number:");
                    option = this.scanner.nextInt();
                    this.scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println("Incorrect input!");
                    e.printStackTrace();
                    continue;
                } catch (NoSuchElementException e) {
                    System.out.println("Failed to read input!");
                    e.printStackTrace();
                    continue;
                } catch (IllegalStateException e) {
                    System.out.println("But the scanner was closed...");
                    e.printStackTrace();
                    continue;
                }
                run = this.optionHelper(option);
            }
            System.out.println("Exit!");
            this.scanner.close();
        }

        private boolean optionHelper(int option) {
            boolean run = true;
            switch (option) {
                case 1 -> this.createUser();
                case 2 -> this.readUser();
                case 3 -> this.updateUser();
                case 4 -> this.deleteUser();
                case 5 -> this.listAllUsers();
                case 6 -> run = false;
                default -> System.out.println("Invalid option!");
            }
            return run;
        }

        private void createUser() {
            User user = new User();
            this.fillUserParams(user);
            user.setCreatedAt(LocalDateTime.now());

            this.userDAO.save(user);
            System.out.println("User has been created!");
        }

        protected void createUser(User user) {
            this.userDAO.save(user);
            System.out.println("User has been created!");
        }

        private void updateUser() {
            System.out.println("Enter user id:");
            Long id = this.scanner.nextLong();
            this.scanner.nextLine();
            User user = this.userDAO.findById(id);
            if (user != null) {
                this.fillUserParams(user);
                this.userDAO.update(user);
                System.out.println("User has been updated!");
            } else System.out.println("User not found!");
        }

        protected void updateUser(Long l, String name, String email, int age) {
            User user = this.userDAO.findById(l);
            if (user != null) {
                user.setName(name);
                user.setAge(age);
                user.setEmail(email);
            } else System.out.println("User not found!");
        }

        private void deleteUser() {
            System.out.println("Enter user id:");
            Long id = this.scanner.nextLong();
            this.scanner.nextLine();
            User user = this.userDAO.findById(id);
            if (user != null) {
                this.userDAO.delete(user);
                System.out.println("User has been deleted!");
            } else System.out.println("User not found!");
        }

        protected void deleteUser(Long id) {
            User user = this.userDAO.findById(id);
            if (user != null) {
                this.userDAO.delete(user);
                System.out.println("User has been deleted!");
            } else System.out.println("User not found");
        }

        private void listAllUsers() {
            List<User> users = this.userDAO.findAll();
            if (users != null && !users.isEmpty()) {
                for (User user : users) {
                    System.out.printf(
                            "Name: %s\nEmail: %s\nAge: %d\nId: %d\n",
                            user.getName(),
                            user.getEmail(),
                            user.getAge(),
                            user.getId()
                    );
                }
            } else System.out.println("There is no users in here!");
        }

        protected List<User> listAllUsers(int unused) {
            return this.userDAO.findAll();
        }

        private void readUser() {
            System.out.println("Enter user id:");
            Long id = this.scanner.nextLong();
            this.scanner.nextLine();
            User user = this.userDAO.findById(id);
            if (user != null) {
                System.out.println("Found user!");
                System.out.printf(
                        "Name: %s\nEmail: %s\nAge: %d\n",
                        user.getName(),
                        user.getEmail(),
                        user.getAge()
                );
            } else System.out.println("User not found!");
        }

        protected User readUser(Long l) {
            return this.userDAO.findById(l);
        }

        private void fillUserParams(User user) {
            System.out.println("Enter user name:");
            user.setName(this.scanner.nextLine());
            System.out.println("Enter user email:");
            user.setEmail(this.scanner.nextLine());
            System.out.println("Enter user age");
            user.setAge(this.scanner.nextInt());
            this.scanner.nextLine();
        }
    }

