package modelance.backend.service.wallet;

public class NoWalletExistsException extends Exception {
    public NoWalletExistsException(String message) {
        super(message);
    }

    public NoWalletExistsException() {
        super("No wallet found. Please try again!");
    }
}
