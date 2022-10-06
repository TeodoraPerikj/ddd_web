package mk.ukim.finki.emt.commentmanagement.domain.model;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class CommentId extends DomainObjectId {

    private CommentId() {
        super(CommentId.randomId(CommentId.class).getId());
    }

    public CommentId(@NonNull String uuid) {
        super(uuid);
    }

    public static CommentId convertFromLong(Long id) {
        String newId = String.valueOf(id);

        CommentId commentId = new CommentId(newId);

        return commentId;
    }
}
