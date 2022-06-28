package YHWLTH.sharing.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDTO {

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "newPassword", example = "1234", required = true)
    private String newPassword;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Schema(description = "newPasswordConfirm", example = "1234", required = true)
    private String newPasswordConfirm;
}