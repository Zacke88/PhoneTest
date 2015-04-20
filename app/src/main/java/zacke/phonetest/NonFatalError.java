package zacke.phonetest;

/**
 * Created by Joakim on 2015-04-20.
 */
public final class NonFatalError extends RuntimeException {

    private static final long serialVersionUID = -6259026017799110412L;

    public NonFatalError(String msg) {
        super(msg);
    }
}
