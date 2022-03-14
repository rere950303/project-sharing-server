package YHWLTH.sharing.repo;

import YHWLTH.sharing.entity.ShareApply;
import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.repo.custom.ShareApplyCustomRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareApplyRepo extends JpaRepository<ShareApply, Long>, ShareApplyCustomRepo {

    List<ShareApply> findByUserAndShareItem(User user, ShareItem shareItem);
}
