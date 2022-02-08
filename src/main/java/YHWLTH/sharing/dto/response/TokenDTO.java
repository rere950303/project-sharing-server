package YHWLTH.sharing.dto.response;

import YHWLTH.sharing.dto.common.CommonResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO extends CommonResult {

    @Schema(description = "userId", example = "1")
    private Long userId;

    @Schema(description = "username", example = "양형욱")
    private String username;

    @Schema(description = "토큰값", example = "Header.Payload.Signature")
    private String token;
}
