package YHWLTH.sharing.context;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserContext extends User {

    private final YHWLTH.sharing.entity.User user;

    public UserContext(YHWLTH.sharing.entity.User user, Collection<SimpleGrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }

    public YHWLTH.sharing.entity.User getUser() {
        return user;
    }
}
