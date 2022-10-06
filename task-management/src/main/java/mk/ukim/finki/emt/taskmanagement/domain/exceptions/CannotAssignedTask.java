package mk.ukim.finki.emt.taskmanagement.domain.exceptions;

public class CannotAssignedTask extends RuntimeException {
    public CannotAssignedTask(String id, String s) {
        super(String.format("Cannot assign task with id: %s, to user with id: %s", s, id));
    }
}
