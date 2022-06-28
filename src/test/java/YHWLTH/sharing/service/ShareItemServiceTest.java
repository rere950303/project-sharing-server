package YHWLTH.sharing.service;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.ShareItemRegisterDTO;
import YHWLTH.sharing.entity.Image;
import YHWLTH.sharing.entity.ItemType;
import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.repo.ShareItemRepo;
import YHWLTH.sharing.repo.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {"active.profile=local2"})
class ShareItemServiceTest {

    @Mock
    ShareItemRepo shareItemRepo;

    @Mock
    UserRepo userRepo;

    @InjectMocks
    ShareItemService shareItemService;

    String path = "/Users/hyungwook/Desktop/test/2a013ad9-b1b0-43bc-8767-acdca1466f97_images.jpeg";

    @Test
    @DisplayName("registerTest")
    public void registerTest() throws Exception {
        ReflectionTestUtils.setField(shareItemService, "path", "/Users/hyungwook/Desktop/test/", String.class);
        MockMultipartFile mockMultipartFile = new MockMultipartFile("images", "sdfsdf.png",
                MediaType.IMAGE_PNG_VALUE, new FileInputStream(path));
        ShareItemRegisterDTO registerDTO = new ShareItemRegisterDTO(1L, ItemType.BICYCLE, "자전거입니다.",
                1000L, List.of(mockMultipartFile), "yhw", 100L);

        User user = new User("yhw", "2014170089", "1234");
        doReturn(Optional.of(user)).when(userRepo).findById(anyLong());
        doReturn(new ShareItem(registerDTO, new ArrayList<>(), user)).when(shareItemRepo).save(any());

        ResponseEntity<CommonResult> result = shareItemService.register(registerDTO);
    }

//    @Test
//    @DisplayName("registerExTest")
//    public void registerExTest() throws Exception {
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("images", "sdfsdf.png",
//                MediaType.TEXT_EVENT_STREAM_VALUE, new FileInputStream(path));
//        ShareItemRegisterDTO registerDTO = new ShareItemRegisterDTO(1L, ItemType.BICYCLE, "자전거입니다.",
//                1000L, List.of(mockMultipartFile), "yhw");
//
//        User user = new User("yhw", "2014170089", "1234");
//        doReturn(Optional.of(user)).when(userRepo).findById(anyLong());
//
//        assertThatThrownBy(() -> shareItemService.register(registerDTO)).isInstanceOf(IllegalArgumentException.class);
//    }

//    @Test
//    @DisplayName("registerFileNameTest")
//    public void registerFileNameTest() throws Exception {
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("images", "yhwjjang.png",
//                MediaType.IMAGE_PNG_VALUE, new FileInputStream(path));
//        ShareItemRegisterDTO registerDTO = new ShareItemRegisterDTO(1L, ItemType.BICYCLE, "자전거입니다.",
//                1000L, List.of(mockMultipartFile), "yhw");
//
//        List<Image> images = shareItemService.storeImages(registerDTO.getImages());
//
//        assertThat(images.size()).isEqualTo(1);
//        assertThat(images.get(0).getOriginalFileName()).isEqualTo("yhwjjang.png");
//        assertThat(images.get(0).getStoredFileName()).contains("_yhwjjang.png");
//    }

//    @Test
//    @DisplayName("registerRealTest")
//    public void registerRealTest() throws Exception {
//        MockMultipartFile mockMultipartFile = new MockMultipartFile("images", "sdfsdf.png",
//                MediaType.IMAGE_PNG_VALUE, new FileInputStream(path));
//        ShareItemRegisterDTO registerDTO = new ShareItemRegisterDTO(1L, ItemType.BICYCLE, "자전거입니다.",
//                1000L, List.of(mockMultipartFile), "yhw");
//    }

    @Test
    @DisplayName("removeSuccessTest")
    public void removeSuccessTest() throws Exception {
        ReflectionTestUtils.setField(shareItemService, "path", "/Users/hyungwook/Desktop/test/", String.class);
        String imageName = "2a013ad9-b1b0-43bc-8767-acdca1466f97_images.jpeg";
        ResponseEntity<CommonResult> result = shareItemService.removeImage(imageName);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("removeFailTest")
    public void removeFailTest() throws Exception {
        ReflectionTestUtils.setField(shareItemService, "path", "/Users/hyungwook/Desktop/test/", String.class);
        String imageName = "b5938483-ea73-4af1-9277-834d8ffec72f_sdfsdf.png";
        ResponseEntity<CommonResult> result = shareItemService.removeImage(imageName);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}