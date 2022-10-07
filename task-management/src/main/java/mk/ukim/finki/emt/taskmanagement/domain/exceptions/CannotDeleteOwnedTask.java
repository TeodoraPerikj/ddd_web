package mk.ukim.finki.emt.taskmanagement.domain.exceptions;

public class CannotDeleteOwnedTask extends RuntimeException {
    public CannotDeleteOwnedTask(String id, String s) {
        super(String.format("Cannot delete owned task with id: %s, to user with id: %s", s, id));
    }
}
