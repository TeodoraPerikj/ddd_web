package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;
import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;

public class UserId extends DomainObjectId {

    public UserId() {
        super(UserId.randomId(UserId.class).getId());
    }

    public UserId(@NonNull String uuid) {
        super(uuid);
    }
}
