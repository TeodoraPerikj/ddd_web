package mk.ukim.finki.emt.taskmanagement.domain.exceptions;

public class CannotDeleteAssignedTask extends RuntimeException {
    public CannotDeleteAssignedTask(String id, String s) {
        super(String.format("Cannot delete assigned task with id: %s, to user with id: %s", s, id));
    }
}
