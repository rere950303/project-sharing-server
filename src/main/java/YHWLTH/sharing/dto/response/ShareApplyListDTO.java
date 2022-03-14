package YHWLTH.sharing.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ShareApplyListDTO {

    @Schema(description = "shareItemId", example = "1")
    private Long shareItemId;

    @Schema(description = "shareApplyId", example = "1")
    private Long shareApplyId;

    @Schema(description = "itemType", example = "학위 가운")
    private String itemType;

    @Schema(description = "rentalFee", example = "10000")
    private Long rentalFee;

    @Schema(description = "deposit", example = "10000")
    private Long deposit;

    @Schema(description = "공유중 여부", example = "false")
    private Boolean isShared;

    @Schema(description = "startDate", example = "2022-03-13")
    private LocalDate startDate;

    @Schema(description = "endDate", example = "2022-03-15")
    private LocalDate endDate;

    @QueryProjection
    public ShareApplyListDTO(Long shareItemId, Long shareApplyId, String itemType, Long rentalFee, Long deposit, Boolean isShared, LocalDate startDate, LocalDate endDate) {
        this.shareItemId = shareItemId;
        this.shareApplyId = shareApplyId;
        this.itemType = itemType;
        this.rentalFee = rentalFee;
        this.deposit = deposit;
        this.isShared = isShared;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
