package YHWLTH.sharing.controller.exhandler;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.dto.response.SignUpResponseDTO;
import YHWLTH.sharing.ex.AlreadyExistsEx;
import YHWLTH.sharing.ex.AuthenticationEx;
import YHWLTH.sharing.ex.SignUpEx;
import YHWLTH.sharing.util.ApiUtil;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.core.convert.ConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.InputMismatchException;

@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult inputMisMatchExHandle(InputMismatchException ex) {

        return ApiUtil.getFailResult(null, ApiUtil.FAIL_BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public CommonResult authenticationExHandle(AuthenticationEx ex) {

        return ApiUtil.getFailResult(null, ApiUtil.FAIL_UNAUTHORIZED, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult alreadyExistsExHandle(AlreadyExistsEx ex) {

        return ApiUtil.getFailResult(null, ApiUtil.FAIL_BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult signUpExHandle(SignUpEx ex) {
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO(null, ex.getSignUpDTO().getUsername());
        ApiUtil.makeFailResult(signUpResponseDTO, ApiUtil.FAIL_BAD_REQUEST, ex.getMessage(), null);

        return signUpResponseDTO;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult usernameNotFoundExHandle(UsernameNotFoundException ex) {

        return ApiUtil.getFailResult(null, ApiUtil.FAIL_BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult badCredentialsExHandle(BadCredentialsException ex) {

        return ApiUtil.getFailResult(null, ApiUtil.FAIL_BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public CommonResult accessDeniedExHandle(AccessDeniedException ex) {
        String message = null;
        if (ex.getMessage().equals("Access is denied")) {
            message = "접근 권한이 없습니다.";
        } else {
            message = ex.getMessage();
        }

        return ApiUtil.getFailResult(null, ApiUtil.FAIL_ACCESS_DENIED, message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult illegalArgumentExHandle(IllegalArgumentException ex) {
        return ApiUtil.getFailResult(null, ApiUtil.FAIL_BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult illegalStateExHandle(IllegalStateException ex) {
        return ApiUtil.getFailResult(null, ApiUtil.FAIL_BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult iOExHandle(IOException ex) {
        return ApiUtil.getFailResult(null, ApiUtil.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult httpMessageConversionExHandle(HttpMessageConversionException ex) {
        return ApiUtil.getFailResult(null, ApiUtil.FAIL_BAD_REQUEST, "형식을 지켜주세요.");
    }
}