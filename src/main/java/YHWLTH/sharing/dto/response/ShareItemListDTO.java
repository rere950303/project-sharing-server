package YHWLTH.sharing.dto.response;

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

    @QueryProjection
    public ShareItemListDTO(Long shareItemId, String itemType, Long rentalFee, Long deposit, Boolean isShared) {
        this.shareItemId = shareItemId;
        this.itemType = itemType;
        this.rentalFee = rentalFee;
        this.deposit = deposit;
        this.isShared = isShared;
    }
}