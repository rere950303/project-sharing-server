package YHWLTH.sharing.controller;

import YHWLTH.sharing.annotation.WithMockCustomUser;
import YHWLTH.sharing.config.JacksonConfig;
import YHWLTH.sharing.dto.request.ReviewRegisterDTO;
import YHWLTH.sharing.entity.ReviewType;
import YHWLTH.sharing.jwt.JwtAccessDeniedHandler;
import YHWLTH.sharing.jwt.JwtAuthenticationEntryPoint;
import YHWLTH.sharing.jwt.TokenProvider;
import YHWLTH.sharing.service.AuthService;
import YHWLTH.sharing.service.ReviewService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
@Import({ObjectMapper.class, JwtAuthenticationEntryPoint.class, HttpEncodingAutoConfiguration.class, JacksonConfig.class})
class ReviewControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TokenProvider tokenProvider;

    @MockBean
    ReviewService reviewService;


    @MockBean
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Test
    @DisplayName("registerFailTest")
    @WithMockCustomUser
    public void registerFailTest() throws Exception {
        ReviewRegisterDTO reviewRegisterDTO = new ReviewRegisterDTO(1L, 11, ReviewType.LENDER, "test");
        String json = objectMapper.writeValueAsString(reviewRegisterDTO);

        mockMvc.perform(post("/api/review").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }


}