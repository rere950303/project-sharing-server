package YHWLTH.sharing.repo;

import YHWLTH.sharing.dto.request.SignUpDTO;
import YHWLTH.sharing.entity.Role;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.entity.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class UserRepoTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepo userRepo;

    @Test
    @DisplayName("signupTest")
    public void signupTest() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO("yhw", "123", "123", "123");
        User user = new User(signUpDTO, new BCryptPasswordEncoder());
        em.persist(user);
        em.flush();

        Optional<User> optional = userRepo.findUserByUsername(signUpDTO.getUsername() + signUpDTO.getStudentId());

        assertThat(optional.get().getUsername()).isEqualTo("yhw" + "123");
        assertThat(optional.get().getStudentId()).isEqualTo(signUpDTO.getStudentId());
    }

    @Test
    @DisplayName("deleteTest")
    public void deleteTest() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO("yhw", "123", "123", "123");
        User user = new User(signUpDTO, new BCryptPasswordEncoder());
        em.persist(user);
        em.flush();

        userRepo.deleteUserByStudentId(signUpDTO.getStudentId());

        List<User> list = userRepo.findAll();
        assertThat(list.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("userSaveTest")
    public void userSaveTest() throws Exception {
        User user = new User("yhw", "1234", "1234");
        Role role1 = new Role("USER");
        Role role2 = new Role("ADMIN");
        UserRole userRole = new UserRole(user, role1);
        UserRole userRole1 = new UserRole(user, role2);
        user.addRoles(userRole);
        user.addRoles(userRole1);

        em.persist(role1);
        em.persist(role2);
        em.persist(userRole);
        em.flush();

        User yhw = userRepo.findUserByUsername("yhw").get();
        List<UserRole> roles = yhw.getRoles();

        assertThat(roles.size()).isEqualTo(2);
        assertThat(roles.get(0).getRole().getRole()).isEqualTo("USER");
        assertThat(roles.get(1).getRole().getRole()).isEqualTo("ADMIN");
    }

}