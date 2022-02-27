package YHWLTH.sharing.dto.response;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.entity.ShareItem;
import YHWLTH.sharing.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class ShareItemReadDTO extends CommonResult {

    @Schema(description = "itemId", example = "1")
    private Long itemId;

    @Schema(description = "소유자 id", example = "1")
    private Long userId;

    @Schema(description = "소유자 이름", example = "양형욱")
    private String username;

    @Schema(description = "kakaoId", example = "yhw")
    private String kakaoId;

    @Schema(description = "itemType", example = "학위가운")
    private String itemType;

    @Schema(description = "desc", example = "카지오 계산기입니다.")
    private String desc;

    @Schema(description = "rentalFee", example = "1000")
    private Long rentalFee;

    @Schema(description = "이미지 파일 이름", example = "xxxxxx.jpg, xxxxxx.jpg")
    private List<String> images = new ArrayList<>();

    public ShareItemReadDTO(ShareItem shareItem, User user) {
        this.userId = user.getId();
        this.rentalFee = shareItem.getRentalFee();
        this.itemType = shareItem.getItemType();
        this.kakaoId = shareItem.getKakaoId();
        this.desc = shareItem.getDesc();
        this.images.addAll(shareItem.getImages().stream().map((i) -> i.getStoredFileName()).collect(Collectors.toList()));
        this.itemId = shareItem.getId();
        this.username = user.getUsername();
    }
}
