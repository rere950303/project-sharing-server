package YHWLTH.sharing.entity;

import YHWLTH.sharing.dto.request.ShareItemRegisterDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "SHAREITEM_SEQ_GENERATOR",
        sequenceName = "SHAREITEM_SEQ",
        allocationSize = 1
)
public class ShareItem {

    @Id
    @Column(name = "shareItem_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SHAREITEM_SEQ_GENERATOR")
    private Long id;

    private Long rentalFee;

    private String itemType;

    private String kakaoId;

    @Lob
    private String desc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "shareItem", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Image> images = new ArrayList<>();

    public ShareItem(ShareItemRegisterDTO registerDTO, List<Image> images, User user) {
        this.rentalFee = registerDTO.getRentalFee();
        this.itemType = registerDTO.getItemType().getDesc();
        this.kakaoId = registerDTO.getKakaoId();
        this.desc = registerDTO.getDesc();
        this.images = images;
        this.user = user;
    }
}