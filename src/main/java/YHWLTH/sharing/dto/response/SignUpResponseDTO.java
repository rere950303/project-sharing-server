package YHWLTH.sharing.dto.response;

import YHWLTH.sharing.dto.common.CommonResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpResponseDTO extends CommonResult {

    @Schema(description = "username", example = "양형욱")
    private String username;

    public SignUpResponseDTO(String username) {
        this.username = username;
    }
}