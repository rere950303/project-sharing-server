package YHWLTH.sharing.controller;

import YHWLTH.sharing.ex.AuthenticationEx;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.util.InputMismatchException;

@RequestMapping("/api/authentication/error")
@Controller
@ApiIgnore
public class AuthErrorController {

    @GetMapping("/loginfail")
    public void errorLoginFail() {
        throw new InputMismatchException("아이디 또는 비밀번호가 일치하지 않습니다.");
    }

    @GetMapping("/unauthenticated")
    public void errorUnauthenticated() {
        throw new AuthenticationEx("인증이 필요합니다.");
    }

    @GetMapping("/denied")
    public void errorDenied() {
        throw new AccessDeniedException("접근 권한이 없습니다.");
    }
}