package YHWLTH.sharing.controller;

import YHWLTH.sharing.annotation.WithMockCustomUser;
import YHWLTH.sharing.config.JacksonConfig;
import YHWLTH.sharing.jwt.JwtAccessDeniedHandler;
import YHWLTH.sharing.jwt.JwtAuthenticationEntryPoint;
import YHWLTH.sharing.jwt.TokenProvider;
import YHWLTH.sharing.service.ShareApplyService;
import YHWLTH.sharing.util.ApiUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShareApplyController.class)
@Import({ObjectMapper.class, JwtAuthenticationEntryPoint.class, HttpEncodingAutoConfiguration.class, JwtAccessDeniedHandler.class, JacksonConfig.class})
class ShareApplyControllerTest {

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @MockBean
    ShareApplyService shareApplyService;

    @MockBean
    TokenProvider tokenProvider;

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.objectMapper = objectMapper;
    }

    @Test
    @DisplayName("applyFailTest1")
    @WithMockUser
    public void applyFailTest1() throws Exception {
        ShareApplyTestDTO shareApplyTestDTO = new ShareApplyTestDTO(1L, "20220213", "20220214");
        String json = objectMapper.writeValueAsString(shareApplyTestDTO);

        mockMvc.perform(post("/api/apply").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(HttpMessageConversionException.class))
                .andDo(print());
    }

    @Test
    @DisplayName("applyFailTest2")
    @WithMockCustomUser
    public void applyFailTest2() throws Exception {
        ShareApplyTestDTO shareApplyTestDTO = new ShareApplyTestDTO(1L, "2022-02-12", "2022-02-12");
        String json = objectMapper.writeValueAsString(shareApplyTestDTO);

        mockMvc.perform(post("/api/apply").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
                .andDo(print());
    }

    @Test
    @DisplayName("applyFailTest3")
    @WithMockCustomUser
    public void applyFailTest3() throws Exception {
        ShareApplyTestDTO shareApplyTestDTO = new ShareApplyTestDTO(null, null, null);
        String json = objectMapper.writeValueAsString(shareApplyTestDTO);

        mockMvc.perform(post("/api/apply").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(MethodArgumentNotValidException.class))
                .andDo(print());
    }

    @Test
    @DisplayName("applySuccessTest1")
    @WithMockCustomUser
    public void applySuccessTest1() throws Exception {
        ShareApplyTestDTO shareApplyTestDTO = new ShareApplyTestDTO(1L, "2022-02-14", "2022-02-14");
        String json = objectMapper.writeValueAsString(shareApplyTestDTO);
        doReturn(new ResponseEntity<>(ApiUtil.getSuccessResult(ApiUtil.SUCCESS_CREATED), HttpStatus.CREATED)).when(shareApplyService).apply(any(), any());

        mockMvc.perform(post("/api/apply").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    static class ShareApplyTestDTO {
        private Long shareItemId;
        private String startDate;
        private String endDate;

        public ShareApplyTestDTO(Long shareItemId, String startDate, String endDate) {
            this.shareItemId = shareItemId;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}