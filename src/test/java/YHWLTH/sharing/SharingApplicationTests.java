package YHWLTH.sharing;

import YHWLTH.sharing.context.UserContext;
import YHWLTH.sharing.controller.AuthController;
import YHWLTH.sharing.dto.request.LoginDTO;
import YHWLTH.sharing.dto.request.SignUpDTO;
import YHWLTH.sharing.dto.response.TokenDTO;
import YHWLTH.sharing.entity.Role;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.entity.UserRole;
import YHWLTH.sharing.jwt.TokenProvider;
import YHWLTH.sharing.service.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@TestPropertySource(properties = {"jwt.key=MjAyMi0wMi0wMS1ZSFdfTFRILnByb2plY3Qtc2hhcmluZy1zZXJ2ZXIuc2tzbXNkaWRndWRkbnJkbGVrLnJrcndrZG1sdGtmYWRtbHFrZHRscmRsZGxUc21zcmp0ZGxyaHNrZWhkdWZ0bGFnbHRrZnJoZGxUZWsuYWhlbmVtZndrZmVoT1RkbWF1c3docnBUcmhza2VoYW5mZmhzcm1mamdlay50a2ZrZGdrc2VrCg==",
"active.profile=local1", "jwt.time=86400", "file.path=/Users/hyungwook/Desktop/test/"
})
class SharingApplicationTests {

	@Autowired
	EntityManager entityManager;

	@Autowired
	AuthController authController;

	@Autowired
	AuthService authService;

	@Autowired
	TokenProvider tokenProvider;

	@Autowired
	MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("JwtTest")
	public void jwtTest() throws Exception {
		User user = new User(new SignUpDTO("yhw", "1234", "1234", "1234"), new BCryptPasswordEncoder());
		entityManager.persist(user);
		entityManager.flush();

		ResponseEntity<TokenDTO> response = authService.login(new LoginDTO("1234", "yhw", "1234"));
		String token = response.getBody().getToken();

		Authentication authentication = tokenProvider.getAuthentication(token);
		ArrayList<? extends GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());

		assertThat(authorities.size()).isEqualTo(1);
		assertThat(authorities.get(0).getAuthority()).isEqualTo("ROLE_USER");

		UserContext principal = (UserContext) authentication.getPrincipal();
		assertThat(principal.getUser().getStudentId()).isEqualTo("1234");
		assertThat(authentication.getPrincipal()).isInstanceOf(UserContext.class);
	}

	@Test
	@DisplayName("getImage_notExist")
	@WithMockUser
	public void getImageNotExist() throws Exception {
		mockMvc.perform(get("/api/board/image").param("imageName", "noImage"))
				.andExpect(status().isOk())
				.andDo(print());
	}

//	@Test
//	@DisplayName("JwtTest2")
//	public void jwtTest2() throws Exception {
//		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//		User user = new User(null, "yhw1234", "1234", passwordEncoder.encode("1234"), true, 0L, null, new ArrayList<>(), null);
//		Role role = new Role("USER");
//		UserRole userRole = new UserRole(user, role);
//		user.addRoles(userRole);
//
//		entityManager.persist(user);
//		entityManager.persist(userRole);
//		entityManager.persist(role);
//		entityManager.flush();
//
//		ResponseEntity<TokenDTO> response = authService.login(new LoginDTO("1234", "yhw", "1234"));
//		String token = response.getBody().getToken();
//
//		Authentication authentication = tokenProvider.getAuthentication(token);
//		Object principal = authentication.getPrincipal();
//
//		assertThat(principal).isInstanceOf(UserContext.class);
//		UserContext userContext = (UserContext) authentication.getPrincipal();
//		assertThat(userContext.getUser().getId()).isNotNull();
//		assertThat(userContext.getUser().getId()).isEqualTo(1L);
//	}

}
