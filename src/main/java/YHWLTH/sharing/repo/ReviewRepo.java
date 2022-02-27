package YHWLTH.sharing.repo;

import YHWLTH.sharing.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Long> {
}
