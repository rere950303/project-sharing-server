package YHWLTH.sharing.entity;

import YHWLTH.sharing.dto.request.SignUpDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        sequenceName = "USER_SEQ",
        allocationSize = 1
)
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    private Long userId;

    @Column(unique = true)
    private String username;

    private String password;

    private Boolean isPermit;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(SignUpDTO signUpDTO, PasswordEncoder passwordEncoder) {
        this.username = signUpDTO.getUsername();
        this.password = passwordEncoder.encode(signUpDTO.getPassword());
        this.role = Role.USER;
        isPermit = false;
    }
}