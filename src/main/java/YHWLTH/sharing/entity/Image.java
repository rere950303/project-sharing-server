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
        name = "IMAGE_SEQ_GENERATOR",
        sequenceName = "IMAGE_SEQ",
        allocationSize = 1
)
public class Image extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMAGE_SEQ_GENERATOR")
    @Column(name = "image_id")
    private Long id;

    private String originalFileName;

    private String storedFileName;

    @ManyToOne
    @JoinColumn(name = "shareItem_id")
    private ShareItem shareItem;

    public Image(String originalFileName, String storedFileName) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
    }

    public void makeMappingShareItem(ShareItem shareItem) {
        this.shareItem = shareItem;
    }
}