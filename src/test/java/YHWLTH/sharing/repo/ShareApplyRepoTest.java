package YHWLTH.sharing.repo;

import YHWLTH.sharing.dto.request.ShareApplyDTO;
import YHWLTH.sharing.entity.ShareApply;
import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class ShareApplyRepoTest {

    @Autowired
    EntityManager em;

    @Autowired
    ShareApplyRepo shareApplyRepo;

    @Test
    @DisplayName("findByUserAndItem")
    public void findByUserAndItem() throws Exception {
        User user1 = new User();
        ShareItem shareItem = new ShareItem();
        em.persist(shareItem);
        em.persist(user1);
        Long id = shareItem.getId();
        ShareApplyDTO shareApplyDTO = new ShareApplyDTO(id, LocalDate.now(), LocalDate.now());
        ShareApply shareApply = new ShareApply(user1, shareItem, shareApplyDTO);
        em.persist(shareApply);
        em.flush();

        List<ShareApply> result1 = shareApplyRepo.findByUserAndShareItem(user1, shareItem);
        assertThat(result1.size()).isEqualTo(1);
        User user2 = new User();
        em.persist(user2);
        em.flush();
        List<ShareApply> result2 = shareApplyRepo.findByUserAndShareItem(user2, shareItem);
        assertThat(result2.size()).isEqualTo(0);
    }

}