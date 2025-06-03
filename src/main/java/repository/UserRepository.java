package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existingByEmail(String email);

    @Override
    Optional<User> findByEmail(String email);
}
