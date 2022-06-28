package YHWLTH.sharing.repo;

import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.repo.custom.ShareItemCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareItemRepo extends JpaRepository<ShareItem, Long>, ShareItemCustomRepo {
}
