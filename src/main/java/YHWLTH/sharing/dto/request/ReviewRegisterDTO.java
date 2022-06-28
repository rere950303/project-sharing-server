package YHWLTH.sharing.dto.request;

import YHWLTH.sharing.entity.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRegisterDTO {

    @NotNull(message = "userId를 입력해주세요.")
    @Schema(description = "userId", example = "1", required = true)
    private Long userId;

    @NotNull(message = "리뷰 점수를 입력해주세요.")
    @Range(max = 10, message = "[0, 10] 범위로 입력해주세요.")
    @Schema(description = "score", example = "1", required = true, allowableValues = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    private Integer score;

    @NotNull(message = "reviewType를 입력해주세요.")
    @Schema(description = "reviewType", example = "LENDER", required = true, allowableValues = {"LENDER", "BORROWER"})
    private ReviewType reviewType;
}
