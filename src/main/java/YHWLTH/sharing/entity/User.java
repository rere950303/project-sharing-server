package YHWLTH.sharing.entity;

import YHWLTH.sharing.dto.request.ReviewRegisterDTO;
import YHWLTH.sharing.dto.request.SignUpDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@SequenceGenerator(
        name = "USER_SEQ_GENERATOR",
        sequenceName = "USER_SEQ",
        allocationSize = 1
)
public class User extends BaseEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
    private Long id;

    @Column(unique = true)
    private String username;

    private String studentId;

    private String password;

    private Boolean isPermit;

    private long point = 0L;

    private long lenderScore = 0L;

    private int lenderReviewNum = 0;

    private long borrowerScore = 0L;

    private int borrowerReviewNum = 0;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ShareItem> shareItemList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<UserRole> roles = new ArrayList<>();

    public User(SignUpDTO signUpDTO, PasswordEncoder passwordEncoder) {
        this.studentId = signUpDTO.getStudentId();
        this.username = signUpDTO.getUsername() + signUpDTO.getStudentId();
        this.password = passwordEncoder.encode(signUpDTO.getPassword());
        isPermit = false;
    }

    public User(String username, String studentId, String password) {
        this.username = username;
        this.studentId = studentId;
        this.password = password;
    }

    public void addRoles(UserRole userRole) {
        this.roles.add(userRole);
    }

    public void addPoint(Long point) {
        this.point += point;
    }

    public void minusPoint(Long point) {
        this.point -= point;
    }

    public void changePassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(newPassword);
    }

    public void registerReview(ReviewRegisterDTO registerDTO) {
        if (registerDTO.getReviewType().equals(ReviewType.LENDER)) {
            lenderReviewNum++;
            lenderScore += registerDTO.getScore();
        } else {
            borrowerReviewNum++;
            borrowerScore += registerDTO.getScore();
        }
    }
}