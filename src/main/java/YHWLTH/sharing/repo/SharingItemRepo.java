package YHWLTH.sharing.repo;

import YHWLTH.sharing.entity.SharingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharingItemRepo extends JpaRepository<SharingItem, Long> {

    Optional<SharingItem> findByShareItemId(Long shareItemId);
}
