package mk.ukim.finki.emt.usermanagement.domain.repository;

import mk.ukim.finki.emt.usermanagement.domain.model.User;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, UserId> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
}
