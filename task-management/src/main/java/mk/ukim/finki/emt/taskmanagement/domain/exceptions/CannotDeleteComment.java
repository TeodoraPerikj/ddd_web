package mk.ukim.finki.emt.taskmanagement.domain.exceptions;

public class CannotDeleteComment extends RuntimeException {
    public CannotDeleteComment(String id, String s) {
        super(String.format("Cannot delete comment with id: %s, from task with id: %s", id, s));
    }
}
