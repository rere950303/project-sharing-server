package YHWLTH.sharing.controller;

import YHWLTH.sharing.annotation.WithMockCustomUser;
import YHWLTH.sharing.config.JacksonConfig;
import YHWLTH.sharing.dto.request.SignUpDTO;
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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import({ObjectMapper.class, JwtAuthenticationEntryPoint.class, HttpEncodingAutoConfiguration.class, JacksonConfig.class})
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
    @DisplayName("withdrawalFailTest")
    @WithMockCustomUser(studentId = "1234")
    public void withdrawalFailTest() throws Exception {
        mockMvc.perform(delete("/api/authentication/{userId}", "2"))
                .andExpect(status().isForbidden())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(AccessDeniedException.class))
                .andDo(print());
    }

    @Test
    @DisplayName("withdrawalFailTest2")
    @WithAnonymousUser
    public void withdrawalFailTest2() throws Exception {
        mockMvc.perform(delete("/api/authentication/{userId}", "123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/authentication/error/unauthenticated"))
                .andDo(print());
    }

    @Test
    @DisplayName("signUpTest")
    public void signUpTest() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO("양형욱", "2014170089", "1234", "1234");
        String json = objectMapper.writeValueAsString(signUpDTO);

        mockMvc.perform(post("/api/authentication/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("signUpFailTest")
    public void signUpFailTest() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO("양형 욱", "2014170089", "1234", "1234");
        String json = objectMapper.writeValueAsString(signUpDTO);

        mockMvc.perform(post("/api/authentication/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("signUpFailTest2")
    public void signUpFailTest2() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO("양형욱", "201417008", "1234", "1234");
        String json = objectMapper.writeValueAsString(signUpDTO);

        mockMvc.perform(post("/api/authentication/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("signUpFailTest3")
    public void signUpFailTest3() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO("양형욱", "201417008x", "1234", "1234");
        String json = objectMapper.writeValueAsString(signUpDTO);

        mockMvc.perform(post("/api/authentication/signup").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}