package YHWLTH.sharing.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@SequenceGenerator(
        name = "USERROLE_SEQ_GENERATOR",
        sequenceName = "USERROLE_SEQ",
        allocationSize = 1)
public class UserRole extends BaseEntity {

    @Id
    @Column(name = "userrole_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERROLE_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }
}