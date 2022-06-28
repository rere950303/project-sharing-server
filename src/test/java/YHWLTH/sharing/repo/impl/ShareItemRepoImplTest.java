package YHWLTH.sharing.repo.impl;

import YHWLTH.sharing.config.JPATestConfig;
import YHWLTH.sharing.dto.request.PageRequestDTO;
import YHWLTH.sharing.dto.request.ShareItemRegisterDTO;
import YHWLTH.sharing.dto.response.ShareItemListDTO;
import YHWLTH.sharing.entity.Image;
import YHWLTH.sharing.entity.ItemType;
import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.repo.ShareItemRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@Import(value = {JPATestConfig.class})
@TestPropertySource(properties = "active.profile=local1")
class ShareItemRepoImplTest {

    @Autowired
    ShareItemRepo shareItemRepo;

    @Autowired
    EntityManager em;

    @BeforeEach
    public void init() throws Exception {
        for (int i = 1; i <= 10; i++) {
            ShareItemRegisterDTO registerDTO = new ShareItemRegisterDTO(null, ItemType.BICYCLE, "자전거",
                    Long.valueOf(i), null, "yhw " + i, Long.valueOf(i));
            Image image = new Image("original_" + i, "" + i);
            ShareItem shareItem = new ShareItem(registerDTO, List.of(image), null);
            image.makeMappingShareItem(shareItem);
            shareItemRepo.save(shareItem);
        }
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("shareItemListTest")
    public void shareItemListTest() throws Exception {
        Page<ShareItemListDTO> result = shareItemRepo.shareItemList(new PageRequestDTO(), null);
        List<ShareItemListDTO> content = result.getContent();
        assertThat(content.size()).isEqualTo(10);
        assertThat(content.get(0).getShareItemId()).isEqualTo(20L);
    }

    @Test
    @DisplayName("shareItemListTest2")
    public void shareItemListTest2() throws Exception {
        PageRequestDTO pageRequestDTO = new PageRequestDTO(2, 5, ItemType.BICYCLE, false, null, null);
        Page<ShareItemListDTO> result = shareItemRepo.shareItemList(pageRequestDTO, null);
        List<ShareItemListDTO> content = result.getContent();
        assertThat(content.size()).isEqualTo(5);
        assertThat(content.get(0).getShareItemId()).isEqualTo(15L);
    }

    @Test
    @DisplayName("shareItemListTest3")
    public void shareItemListTest3() throws Exception {
        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 5, ItemType.BICYCLE, false, null, 5L);
        Page<ShareItemListDTO> result = shareItemRepo.shareItemList(pageRequestDTO, null);
        List<ShareItemListDTO> content = result.getContent();
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(content.size()).isEqualTo(5);
        assertThat(content.get(0).getShareItemId()).isEqualTo(5L);
    }

    @Test
    @DisplayName("shareItemListTest4")
    public void shareItemListTest4() throws Exception {
        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 10, ItemType.BICYCLE, false, null, null);
        Page<ShareItemListDTO> result = shareItemRepo.shareItemList(pageRequestDTO, null);
        List<ShareItemListDTO> content = result.getContent();
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(content.size()).isEqualTo(10);
        assertThat(content.get(0).getImage()).isEqualTo("10");
    }
}