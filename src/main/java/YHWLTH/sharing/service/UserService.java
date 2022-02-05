package YHWLTH.sharing.service;

import YHWLTH.sharing.context.UserContext;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.entity.UserRole;
import YHWLTH.sharing.ex.AuthenticationEx;
import YHWLTH.sharing.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, AuthenticationEx {
        User user = userRepo.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("데이터베이스에서 찾을수 없습니다."));

        if (!user.getIsPermit()) {
            throw new AuthenticationEx("학생증 인증을 먼저 받아주세요.");
        }

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (UserRole role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().getRole()));
        }

        return new UserContext(user, authorities);
    }
}