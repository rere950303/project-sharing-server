package YHWLTH.sharing.repo;

import YHWLTH.sharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    void deleteUserByUsername(String username);
}
