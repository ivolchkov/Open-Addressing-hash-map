package ua.com.test.task.exception;

public class OutOfSpaceException extends RuntimeException {
    public OutOfSpaceException(String s) {
        super(s);
    }

    public OutOfSpaceException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
