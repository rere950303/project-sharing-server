package YHWLTH.sharing.context;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UserContext extends User {

    private final YHWLTH.sharing.entity.User user;

    public UserContext(YHWLTH.sharing.entity.User user) {
        super(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole())));
        this.user = user;
    }

    public YHWLTH.sharing.entity.User getUser() {
        return user;
    }
}
