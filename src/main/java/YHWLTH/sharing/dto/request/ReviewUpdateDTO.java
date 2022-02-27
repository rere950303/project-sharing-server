package YHWLTH.sharing.dto.request;

import YHWLTH.sharing.entity.ReviewType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewUpdateDTO {

    @NotNull(message = "reviewId를 입력해주세요.")
    @Schema(description = "reviewId", example = "1", required = true)
    private Long reviewId;

    @Max(value = 10, message = "10 이하로 입력해주세요.")
    @Min(value = 0, message = "0 이상으로 입력해주세요.")
    @Schema(description = "score", example = "1", required = true, allowableValues = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    private Integer score;

    @Schema(description = "reviewType", example = "lender", required = true)
    private ReviewType reviewType;

    @Schema(description = "리뷰 내용", example = "물건 상태가 양호합니다. 만족합니다.")
    private String content;
}
