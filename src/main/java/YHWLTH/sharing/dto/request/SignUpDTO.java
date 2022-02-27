package YHWLTH.sharing.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {

    @NotBlank(message = "이름을 입력해주세요.")
    @Pattern(regexp = "[^ ][^ ]+", message = "이름 형식을 지켜주세요.")
    @Schema(description = "username", example = "양형욱", required = true)
    private String username;

    @NotBlank(message = "학번을 입력해주세요.")
    @Pattern(regexp = "\\d{10}", message = "학번 형식을 지켜주세요.")
    @Schema(description = "studentId", example = "2014xxxxxx", required = true)
    private String studentId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "password", example = "1234", required = true)
    private String password;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "passwordConfirm", example = "1234", required = true)
    private String passwordConfirm;
}
