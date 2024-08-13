package pet.proj.todo.exciption;

public class WrongStatusException extends RuntimeException {
    public WrongStatusException(String message) {
        super(message);
    }

    public WrongStatusException(String message, Throwable cause) {
        super(message, cause);
    }

}
