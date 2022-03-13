package YHWLTH.sharing.dto.response;

import YHWLTH.sharing.dto.common.CommonResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointDTO extends CommonResult {

    @Schema(description = "point", example = "10000")
    private Long point;
}
