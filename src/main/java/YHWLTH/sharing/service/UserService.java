package YHWLTH.sharing.service;

import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.ex.AuthenticationEx;
import YHWLTH.sharing.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().getRole())
                .build();
    }
}
