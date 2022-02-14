package YHWLTH.sharing.repo;

import YHWLTH.sharing.entity.ShareApply;
import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareApplyRepo extends JpaRepository<ShareApply, Long> {

    List<ShareApply> findByUserAndShareItem(User user, ShareItem shareItem);
}
