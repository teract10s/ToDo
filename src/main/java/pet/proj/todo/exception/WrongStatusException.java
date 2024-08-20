package pet.proj.todo.exception;

public class WrongStatusException extends RuntimeException {
    public WrongStatusException(String message) {
        super(message);
    }

    public WrongStatusException(String message, Throwable cause) {
        super(message, cause);
    }

}
