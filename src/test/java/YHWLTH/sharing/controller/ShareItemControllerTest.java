package YHWLTH.sharing.controller;

import YHWLTH.sharing.config.JacksonConfig;
import YHWLTH.sharing.dto.request.ShareItemRegisterDTO;
import YHWLTH.sharing.entity.ItemType;
import YHWLTH.sharing.jwt.JwtAccessDeniedHandler;
import YHWLTH.sharing.jwt.JwtAuthenticationEntryPoint;
import YHWLTH.sharing.jwt.TokenProvider;
import YHWLTH.sharing.service.ShareItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShareItemController.class)
@Import({ObjectMapper.class, JwtAuthenticationEntryPoint.class, HttpEncodingAutoConfiguration.class, JacksonConfig.class})
class ShareItemControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ShareItemService shareItemService;

    @MockBean
    TokenProvider tokenProvider;

    @MockBean
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Test
    @DisplayName("deleteImageFailTest")
    @WithMockUser
    public void deleteImageFailTest() throws Exception {
        mockMvc.perform(delete("/api/shareItem/image").param("imageName", ""))
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(ConstraintViolationException.class))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.result").value("FAIL"))
                .andExpect(jsonPath("$.validationMsg.['deleteImage.imageName']").value("ShareItemController.deleteImage.imageName must not be blank"))
                .andDo(print());
    }

    @Test
    @DisplayName("registerFailTest")
    @WithMockUser
    public void registerFailTest() throws Exception {
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("userId", List.of("1"));
        map.put("itemType", List.of("CAL"));
        map.put("desc", List.of("test"));
        map.put("rentalFee", List.of("1"));
        map.put("kakaoId", List.of("test"));
        map.put("deposit", List.of("1"));

        mockMvc.perform(post("/api/shareItem").contentType(MediaType.MULTIPART_FORM_DATA).params(map))
                .andExpect(status().isCreated())
                .andDo(print());
    }



}