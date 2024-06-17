package modelance.backend.service.account;

public class QueryMismatchException extends Exception {
    public QueryMismatchException(String message) {
        super(message);
    }

    public QueryMismatchException() {
        super("Role tables are mismatched. Please try again!");
    }
}
