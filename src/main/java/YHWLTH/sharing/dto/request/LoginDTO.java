package YHWLTH.sharing.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "이름을 입력해주세요.")
    @Schema(description = "username", example = "양형욱", required = true)
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "password", example = "1234", required = true)
    private String password;
}
