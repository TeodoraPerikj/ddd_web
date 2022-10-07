package mk.ukim.finki.emt.usermanagement.domain.exceptions;

public class CannotDeleteTaskAssigned extends RuntimeException {
    public CannotDeleteTaskAssigned(String id, String id1) {
        super(String.format("Cannot delete assigned task with id: %s, to user with id: %s", id1, id));
    }
}
