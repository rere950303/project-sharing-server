package YHWLTH.sharing.repo;

import YHWLTH.sharing.entity.SharingItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharingItemRepo extends JpaRepository<SharingItem, Long> {
}
