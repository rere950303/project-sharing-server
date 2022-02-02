package YHWLTH.sharing.dto.response;

import YHWLTH.sharing.dto.common.CommonResult;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignUpResponseDTO extends CommonResult {
    private String username;

    public SignUpResponseDTO(String username) {
        this.username = username;
    }
}