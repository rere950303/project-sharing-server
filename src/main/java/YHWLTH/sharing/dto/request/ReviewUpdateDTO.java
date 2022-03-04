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
public class ReviewUpdateDTO {

    @NotNull(message = "reviewId를 입력해주세요.")
    @Schema(description = "reviewId", example = "1", required = true)
    private Long reviewId;

    @Range(max = 10, message = "[0, 10] 범위로 입력해주세요.")
    @Schema(description = "score", example = "1", required = true, allowableValues = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"})
    private Integer score;

    @Schema(description = "reviewType", example = "LENDER", required = true)
    private ReviewType reviewType;

    @Schema(description = "리뷰 내용", example = "물건 상태가 양호합니다. 만족합니다.")
    private String content;
}
