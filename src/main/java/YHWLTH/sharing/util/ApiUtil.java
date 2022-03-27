package YHWLTH.sharing.util;

import YHWLTH.sharing.dto.common.CommonResult;

import java.util.Arrays;
import java.util.HashMap;

public final class ApiUtil {

    private ApiUtil() {
        throw new AssertionError("생성할 수 없는 클래스입니다.");
    }

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    public static final String SUCCESS_OK = "200";
    public static final String SUCCESS_CREATED = "201";
    public static final String SUCCESS_NO_CONTENT = "204";

    public static final String FAIL_BAD_REQUEST = "400";
    public static final String FAIL_UNAUTHORIZED = "401";
    public static final String FAIL_ACCESS_DENIED = "403";
    public static final String FAIL_NOT_FOUND = "404";
    public static final String INTERNAL_SERVER_ERROR = "500";
    public static final String PAYMENT_REQUIRED = "402";

    public static void makeSuccessResult(CommonResult successResult, String code) {
        successResult.setResult(SUCCESS);
        successResult.setCode(code);
    }

    public static void makeFailResult(CommonResult errorResult, String code, String msg, HashMap<String, String> validationMsg) {
        errorResult.setValidationMsg(validationMsg);
        errorResult.setResult(FAIL);
        errorResult.setCode(code);
        errorResult.setMsg(msg);
    }

    public static CommonResult getSuccessResult(String code) {
        CommonResult successResult = new CommonResult();
        makeSuccessResult(successResult, code);

        return successResult;
    }

    public static CommonResult getFailResult(HashMap<String, String> validationMsg, String code, String msg) {
        CommonResult errorResult = new CommonResult();
        makeFailResult(errorResult, code, msg, validationMsg);

        return errorResult;
    }
}
