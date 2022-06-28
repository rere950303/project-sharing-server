package YHWLTH.sharing.dto.response;

import YHWLTH.sharing.entity.ShareItem;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShareItemListDTO {

    @Schema(description = "shareItemId", example = "1")
    private Long shareItemId;

    @Schema(description = "itemType", example = "학위 가운")
    private String itemType;

    @Schema(description = "rentalFee", example = "10000")
    private Long rentalFee;

    @Schema(description = "deposit", example = "10000")
    private Long deposit;

    @Schema(description = "공유중 여부", example = "false")
    private Boolean isShared;

    @Schema(description = "썸네일 이미지", example = "xxxx.png")
    private String image = "";

    public ShareItemListDTO(ShareItem shareItem) {
        this.shareItemId = shareItem.getId();
        this.itemType = shareItem.getItemType();
        this.rentalFee = shareItem.getRentalFee();
        this.deposit = shareItem.getDeposit();
        this.isShared = shareItem.getIsShared();
        if (!shareItem.getImages().isEmpty()) {
            this.image = shareItem.getImages().get(0).getStoredFileName();
        }
    }
}