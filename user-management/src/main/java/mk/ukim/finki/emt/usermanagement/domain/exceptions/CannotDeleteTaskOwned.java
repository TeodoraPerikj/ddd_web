package mk.ukim.finki.emt.usermanagement.domain.exceptions;

    public class CannotDeleteTaskOwned extends RuntimeException {
    public CannotDeleteTaskOwned(String id, String id1) {
        super(String.format("Cannot delete owned task with id: %s, to user with id: %s", id1, id));
    }
}
