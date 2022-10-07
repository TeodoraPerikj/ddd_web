package mk.ukim.finki.emt.commentmanagement.domain.exceptions;

public class CannotSetCommentToTask extends RuntimeException {
    public CannotSetCommentToTask(String s, String id) {
        super(String.format("Cannot set comment with id: %s to task with id: %s", id, s));
    }
}
