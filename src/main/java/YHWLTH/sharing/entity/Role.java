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
        name = "ROLE_SEQ_GENERATOR",
        sequenceName = "ROLE_SEQ",
        allocationSize = 1
)
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ_GENERATOR")
    private Long id;

    private String role;

    public Role(String role) {
        this.role = role;
    }
}
