package ua.com.test.task.exception;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String s) {
        super(s);
    }

    public ElementNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
