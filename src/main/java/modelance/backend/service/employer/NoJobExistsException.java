package modelance.backend.service.employer;

public class NoJobExistsException extends Exception {
    public NoJobExistsException(String message) {
        super(message);
    }

    public NoJobExistsException() {
        super("No job exists!");
    } 
}
