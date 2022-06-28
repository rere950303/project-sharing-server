package YHWLTH.sharing.ex;

public class AuthenticationEx extends RuntimeException {
    public AuthenticationEx() {
        super();
    }

    public AuthenticationEx(String message) {
        super(message);
    }

    public AuthenticationEx(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationEx(Throwable cause) {
        super(cause);
    }

    protected AuthenticationEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
