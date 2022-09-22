package mk.ukim.finki.emt.commentmanagement.domain.valueobjects;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class TaskId extends DomainObjectId {

    private TaskId() {
        super(TaskId.randomId(TaskId.class).getId());
    }

    public TaskId(String uuid) {
        super(uuid);
    }

}
