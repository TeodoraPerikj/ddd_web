package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class CommentId  extends DomainObjectId {

    private CommentId() {
        super(CommentId.randomId(CommentId.class).getId());
    }

    public CommentId(@NonNull String uuid) {
        super(uuid);
    }
}
