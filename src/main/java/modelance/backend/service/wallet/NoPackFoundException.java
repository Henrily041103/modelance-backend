package modelance.backend.service.wallet;

public class NoPackFoundException extends Exception {
    public NoPackFoundException() {
        super("No pack was found!");
    }

    public NoPackFoundException(String message) {
        super(message);
    }
}
