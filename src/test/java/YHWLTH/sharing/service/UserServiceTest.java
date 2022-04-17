package YHWLTH.sharing.service;

import YHWLTH.sharing.entity.Role;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.ex.AuthenticationEx;
import YHWLTH.sharing.repo.UserRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    UserService userService;

//    @Test
//    @DisplayName("AuthenticationEXTest")
//    public void authenticationExTest() throws Exception {
//        doReturn(Optional.of(new User(1L, "yhw", "123", false, Role.USER))).when(userRepo).findUserByUsername("yhw");
//        assertThatThrownBy(() -> userService.loadUserByUsername("yhw")).isInstanceOf(AuthenticationEx.class);
//    }

}