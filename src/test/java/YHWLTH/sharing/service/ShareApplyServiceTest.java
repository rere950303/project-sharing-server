package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.request.ShareApplyDTO;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.repo.ShareApplyRepo;
import YHWLTH.sharing.repo.ShareItemRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ShareApplyServiceTest {

    @Mock
    ShareItemRepo shareItemRepo;

    @Mock
    ShareApplyRepo shareApplyRepo;

    @InjectMocks
    ShareApplyService shareApplyService;

    @Test
    @DisplayName("applyFailTest")
    public void applyFailTest() throws Exception {
        ShareApplyDTO shareApplyDTO = new ShareApplyDTO(1L, LocalDate.of(2022, 02, 13), LocalDate.of(2022, 02, 12));

        assertThatThrownBy(() -> shareApplyService.apply(shareApplyDTO, new User())).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> shareApplyService.apply(shareApplyDTO, new User())).hasMessageContaining("endDate가 startDate보다 이전일 수 없습니다.");
    }
}