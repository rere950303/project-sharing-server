package YHWLTH.sharing.security;

import YHWLTH.sharing.annotation.WithMockCustomUser;
import YHWLTH.sharing.context.UserContext;
import YHWLTH.sharing.dto.request.SignUpDTO;
import YHWLTH.sharing.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.ArrayList;
import java.util.List;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        User user = new User(1L, annotation.username(), annotation.studentId(), annotation.password(), true,
                100L, 100L, 100, 100L, 100, null, null);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(annotation.role()));

        UserContext userContext = new UserContext(user, authorities);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userContext, null, List.of(new SimpleGrantedAuthority(annotation.role())));

        context.setAuthentication(usernamePasswordAuthenticationToken);

        return context;
    }
}
