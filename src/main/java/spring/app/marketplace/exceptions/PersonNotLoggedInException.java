package spring.app.marketplace.exceptions;

public class PersonNotLoggedInException extends RuntimeException {
    public PersonNotLoggedInException(String message) {
        super(message);
    }
}
