package spring.app.marketplace1.exceptions;

public class PersonNotLoggedInException extends RuntimeException {
    public PersonNotLoggedInException(String message) {
        super(message);
    }
}
