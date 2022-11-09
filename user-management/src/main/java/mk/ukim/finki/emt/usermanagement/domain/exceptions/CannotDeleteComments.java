package mk.ukim.finki.emt.usermanagement.domain.exceptions;

public class CannotDeleteComments extends RuntimeException {
    public CannotDeleteComments(String id) {
        super(String.format("Cannot delete comments for user with id: %s", id));
    }
}
