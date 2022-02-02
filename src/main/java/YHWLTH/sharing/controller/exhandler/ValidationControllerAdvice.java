package YHWLTH.sharing.controller.exhandler;

import YHWLTH.sharing.dto.common.CommonResult;
import YHWLTH.sharing.util.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@RestControllerAdvice
@RequiredArgsConstructor
public class ValidationControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult methodArgumentNotValidExHandle(MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        HashMap validationMsg = getValidationMsg(fieldErrors);

        return ApiUtil.getFailResult(validationMsg, ApiUtil.FAIL_BAD_REQUEST, null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult bindExHandle(BindException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        HashMap validationMsg = getValidationMsg(fieldErrors);

        return ApiUtil.getFailResult(validationMsg, ApiUtil.FAIL_BAD_REQUEST, null);
    }

    public HashMap getValidationMsg(List<FieldError> fieldErrors) {
        HashMap<String, String> validationMsg = new HashMap<>();

        for (FieldError error : fieldErrors) {
            String message = Arrays.stream(Objects.requireNonNull(error.getCodes()))
                    .map(c -> {
                        Object[] arguments = error.getArguments();
                        try {
                            return messageSource.getMessage(c, arguments, null);
                        } catch (NoSuchMessageException e) {
                            return null;
                        }
                    }).filter(Objects::nonNull)
                    .findFirst()
                    .orElse(error.getDefaultMessage());

            validationMsg.put(error.getField(), message);
        }

        return validationMsg;
    }
}
