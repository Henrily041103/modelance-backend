package modelance.backend.service.wallet;

public class NotEnoughMoneyException extends Exception {
    public NotEnoughMoneyException() {
        super("Not enough money in bank");
    }

    public NotEnoughMoneyException(String message) {
        super(message);
    }
}
