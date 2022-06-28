package YHWLTH.sharing.dto.response;

import YHWLTH.sharing.dto.common.CommonResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpResponseDTO extends CommonResult {

    @Schema(description = "userId", example = "1")
    private Long userId;

    @Schema(description = "username", example = "양형욱")
    private String username;

    public SignUpResponseDTO(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}