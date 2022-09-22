package mk.ukim.finki.emt.taskmanagement.domain.model;

import lombok.NonNull;
import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

public class TaskId extends DomainObjectId {

    private TaskId() {
        super(TaskId.randomId(TaskId.class).getId());
    }

    public TaskId(@NonNull String uuid) {
        super(uuid);
    }

    public static TaskId convertFromLong(Long id) {
        String newId = String.valueOf(id);

        TaskId taskId = new TaskId(newId);

        return taskId;
    }
}
