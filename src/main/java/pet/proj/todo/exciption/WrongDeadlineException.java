package pet.proj.todo.exciption;

public class WrongDeadlineException extends RuntimeException {
    public WrongDeadlineException(String message) {
        super(message);
    }

    public WrongDeadlineException(String message, Throwable cause) {
        super(message, cause);
    }
}
