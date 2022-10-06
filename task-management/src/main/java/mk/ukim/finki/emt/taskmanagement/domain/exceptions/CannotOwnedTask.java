package mk.ukim.finki.emt.taskmanagement.domain.exceptions;

public class CannotOwnedTask extends RuntimeException {
    public CannotOwnedTask(String id, String s) {
        super(String.format("Cannot own task with id: %s, to user with id: %s", s, id));
    }
}
