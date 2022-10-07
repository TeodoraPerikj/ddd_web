package mk.ukim.finki.emt.commentmanagement.domain.exceptions;

public class CannotDeleteCommentFromTask extends RuntimeException {
    public CannotDeleteCommentFromTask(String id, String id1) {
        super(String.format("Cannot delete comment with id: %s, from task with id: %s", id, id1));
    }
}
