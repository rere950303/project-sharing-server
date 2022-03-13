package YHWLTH.sharing.controller;

import YHWLTH.sharing.annotation.secuirty.CurrentUser;
import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.LoginDTO;
import YHWLTH.sharing.dto.request.PasswordChangeDTO;
import YHWLTH.sharing.dto.request.SignUpDTO;
import YHWLTH.sharing.dto.response.SignUpResponseDTO;
import YHWLTH.sharing.dto.response.TokenDTO;
import YHWLTH.sharing.entity.User;
import YHWLTH.sharing.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@Api(tags = "AuthController")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = SignUpResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "회원가입", description = "회원가입을 진행하는 메소드")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SignUpResponseDTO> signup(@Valid @RequestBody SignUpDTO signUpDTO) throws Exception {

        return authService.signUp(signUpDTO);
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공", content = @Content(schema = @Schema(implementation = TokenDTO.class))),
            @ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "로그인", description = "로그인을 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {

        return authService.login(loginDTO);
    }

    @PostMapping("/logout")
    @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    @Operation(summary = "로그아웃", description = "로그아웃을 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommonResult> logout(HttpServletRequest request) {

        return authService.logout(request);
    }

    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "400", description = "비밀번호 변경 실패", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "비밀번호 변경", description = "비밀번호 변경을 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommonResult> changePassword(@Valid @RequestBody PasswordChangeDTO passwordChangeDTO, @ApiIgnore @CurrentUser User user) {

        return authService.changePassword(passwordChangeDTO, user);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize(value = "#user.id == #userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "탈퇴 성공", content = @Content(schema = @Schema(implementation = CommonResult.class))),
            @ApiResponse(responseCode = "403", description = "탈퇴 권한 없음", content = @Content(schema = @Schema(implementation = CommonResult.class)))
    })
    @Operation(summary = "탈퇴", description = "탈퇴를 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommonResult> delete(@PathVariable Long userId, @ApiIgnore @CurrentUser User user, @ApiIgnore HttpServletRequest request) {

        return authService.delete(userId, request);
    }
}