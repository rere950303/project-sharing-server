package YHWLTH.sharing.ex;

import YHWLTH.sharing.dto.request.SignUpDTO;
import lombok.Getter;

@Getter
public class SignUpEx extends RuntimeException {

    private SignUpDTO signUpDTO;

    public SignUpEx() {
        super();
    }

    public SignUpEx(String message) {
        super(message);
    }

    public SignUpEx(String message, Throwable cause) {
        super(message, cause);
    }

    public SignUpEx(Throwable cause) {
        super(cause);
    }

    protected SignUpEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SignUpEx(String message, SignUpDTO signUpDTO) {
        super(message);
        this.signUpDTO = signUpDTO;
    }
}
