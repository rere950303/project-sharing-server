package YHWLTH.sharing.ex;

import lombok.Getter;

@Getter
public class AlreadyExistsEx extends Exception {
    public AlreadyExistsEx() {
        super();
    }

    public AlreadyExistsEx(String message) {
        super(message);
    }

    public AlreadyExistsEx(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsEx(Throwable cause) {
        super(cause);
    }

    protected AlreadyExistsEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
