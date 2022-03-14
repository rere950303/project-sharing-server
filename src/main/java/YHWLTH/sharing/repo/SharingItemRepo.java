package YHWLTH.sharing.repo;

import YHWLTH.sharing.entity.SharingItem;
import YHWLTH.sharing.repo.custom.SharingItemCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharingItemRepo extends JpaRepository<SharingItem, Long>, SharingItemCustomRepo {

    Optional<SharingItem> findByShareItemId(Long shareItemId);
}
