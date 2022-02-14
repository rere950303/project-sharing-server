package YHWLTH.sharing.entity;

import YHWLTH.sharing.dto.request.ShareApplyDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "SHAREAPPLY_SEQ_GENERATOR",
        sequenceName = "SHAREAPPLY_SEQ",
        allocationSize = 1
)
public class ShareApply {

    @Id
    @Column(name = "shareApply_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHAREAPPLY_SEQ_GENERATOR")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "shareItem_id")
    private ShareItem shareItem;

    private LocalDate starTime;
    private LocalDate endTime;

    public ShareApply(User user, ShareItem shareItem, ShareApplyDTO shareApplyDTO) {
        this.user = user;
        this.shareItem = shareItem;
        this.starTime = shareApplyDTO.getStartDate();
        this.endTime = shareApplyDTO.getEndDate();
    }
}
