package YHWLTH.sharing.ex;

public class PointShortageEx extends RuntimeException {
    public PointShortageEx() {
        super();
    }

    public PointShortageEx(String message) {
        super(message);
    }

    public PointShortageEx(String message, Throwable cause) {
        super(message, cause);
    }

    public PointShortageEx(Throwable cause) {
        super(cause);
    }

    protected PointShortageEx(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
