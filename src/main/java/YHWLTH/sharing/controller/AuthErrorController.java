package YHWLTH.sharing.controller;

import YHWLTH.sharing.ex.AuthenticationEx;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.InputMismatchException;

@RequestMapping("/api/authentication/error")
@Api(tags = {"인증 오류시 예외를 던지는 Controller"})
@Controller
public class AuthErrorController {

    @GetMapping("/loginfail")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "로그인 실패")
    })
    @ApiOperation(value = "로그인 오류를 처리하는 메소드")
    public void errorLoginFail() {
        throw new InputMismatchException("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    @GetMapping("/unauthenticated")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "미인증")
    })
    @ApiOperation(value = "미인증 오류를 처리하는 메소드")
    public void errorUnauthenticated() {
        throw new AuthenticationEx("인증이 필요합니다.");
    }
}
