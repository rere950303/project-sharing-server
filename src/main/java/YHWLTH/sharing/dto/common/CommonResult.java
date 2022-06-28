package YHWLTH.sharing.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {

    @Schema(description = "결과", example = "SUCCESS")
    private String result;

    @Schema(description = "응답 코드", example = "200")
    private String code;

    @Schema(description = "메시지", example = "인증을 먼저 받아주세요.")
    private String msg;

    @Schema(description = "검증 메시지", example = "공백일수 없습니다.")
    private HashMap<String, String> validationMsg = new HashMap<>();
}