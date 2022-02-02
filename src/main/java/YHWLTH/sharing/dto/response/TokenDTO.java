package YHWLTH.sharing.dto.response;

import YHWLTH.sharing.dto.common.CommonResult;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO extends CommonResult {

    private String username;
    private String token;
}
