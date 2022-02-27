package YHWLTH.sharing.entity;

import YHWLTH.sharing.dto.request.ReviewRegisterDTO;
import YHWLTH.sharing.dto.request.ReviewUpdateDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@SequenceGenerator(
        name = "REVIEW_SEQ_GENERATOR",
        sequenceName = "REVIEW_SEQ",
        allocationSize = 1
)
public class Review {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_SEQ_GENERATOR")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String writer;

    private Long writerId;

    private Integer score;

    private String reviewType;

    @Lob
    private String content;

    public Review(ReviewRegisterDTO registerDTO, User target, User writer) {
        this.user = target;
        this.writer = writer.getUsername();
        this.writerId = writer.getId();
        this.score = registerDTO.getScore();
        this.reviewType = registerDTO.getReviewType().getDesc();
        this.content = registerDTO.getContent();
        target.getReviewList().add(this);
    }

    public void update(ReviewUpdateDTO updateDTO) {
        this.score = updateDTO.getScore() == null ? this.score : updateDTO.getScore();
        this.reviewType = updateDTO.getReviewType() == null ? this.reviewType : updateDTO.getReviewType().getDesc();
        this.content = updateDTO.getContent() == null ? this.content : updateDTO.getContent();
    }
}