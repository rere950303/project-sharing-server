package YHWLTH.sharing.dto.response;

import YHWLTH.sharing.dto.common.CommonResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewScoreDTO extends CommonResult {

    @Schema(description = "lender로서의 평점 평균", example = "5")
    private Integer lenderScore;

    @Schema(description = "borrower로서의 평점 평균", example = "5")
    private Integer borrowerScore;
}