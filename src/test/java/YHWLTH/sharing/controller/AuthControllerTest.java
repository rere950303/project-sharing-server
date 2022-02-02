package YHWLTH.sharing.controller;

import YHWLTH.sharing.dto.request.LoginDTO;
import YHWLTH.sharing.ex.AuthenticationEx;
import YHWLTH.sharing.jwt.JwtAccessDeniedHandler;
import YHWLTH.sharing.jwt.JwtAuthenticationEntryPoint;
import YHWLTH.sharing.jwt.TokenProvider;
import YHWLTH.sharing.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({ObjectMapper.class, JwtAuthenticationEntryPoint.class, HttpEncodingAutoConfiguration.class})
class AuthControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    AuthService authService;

    @MockBean
    TokenProvider tokenProvider;

    @MockBean
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Test
    @DisplayName("loginFailTest")
    public void loginFailTest() throws Exception {
        LoginDTO loginDTO = new LoginDTO("yhw", "123");
        doThrow(new AuthenticationEx("학생증 인증을 먼저 받으세요.")).when(authService).login(loginDTO);
        String json = objectMapper.writeValueAsString(loginDTO);

        mockMvc.perform(post("/api/authentication/login").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(AuthenticationEx.class))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage()).isEqualTo("학생증 인증을 먼저 받으세요."))
                .andDo(print());
    }

}