package YHWLTH.sharing.controller;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.request.LoginDTO;
import YHWLTH.sharing.dto.request.SignUpDTO;
import YHWLTH.sharing.dto.response.SignUpResponseDTO;
import YHWLTH.sharing.dto.response.TokenDTO;
import YHWLTH.sharing.service.AuthService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor
@Api(tags = {"회원가입, 로그인, 인증 오류를 제공하는 Controller"})
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ApiImplicitParam(name = "signUpDTO", required = true, dataTypeClass = SignUpDTO.class, paramType = "body")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "회원 가입 성공", response = SignUpResponseDTO.class),
            @ApiResponse(code = 400, message = "회원 가입 실패", response = CommonResult.class)
    })
    @ApiOperation(value = "회원가입을 진행하는 메소드")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SignUpResponseDTO> signup(@Valid @RequestBody SignUpDTO signUpDTO) throws Exception {

        return authService.signUp(signUpDTO);
    }

    @PostMapping("/login")
    @ApiImplicitParam(name = "loginDTO", required = true, dataTypeClass = LoginDTO.class, paramType = "body")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "로그인 성공", response = TokenDTO.class),
            @ApiResponse(code = 400, message = "로그인 실패", response = CommonResult.class)
    })
    @ApiOperation(value = "로그인을 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) throws Exception {

        return authService.login(loginDTO);
    }

    @PostMapping("/logout")
    @ApiResponse(code = 200, message = "로그아웃 성공", response = CommonResult.class)
    @ApiOperation(value = "로그아웃을 진행하는 메소드")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommonResult> logout(HttpServletRequest request) {

        return authService.logout(request);
    }
}
