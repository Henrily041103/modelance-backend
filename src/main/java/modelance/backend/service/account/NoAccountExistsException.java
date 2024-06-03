package modelance.backend.service.account;

public class NoAccountExistsException extends Exception {
    public NoAccountExistsException(String message) {
        super(message);
    }

    public NoAccountExistsException() {
        super("No accounts found. Please try again!");
    }
}
