package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PointServiceTest {

    PointService pointService = new PointService();


    @Test
    @DisplayName("addPointTest")
    public void addPointTest() throws Exception {
        User user = new User();
        ResponseEntity<CommonResult> result = pointService.addPoint(100L, user);

        assertThat(user.getPoint()).isEqualTo(100L);
    }

}