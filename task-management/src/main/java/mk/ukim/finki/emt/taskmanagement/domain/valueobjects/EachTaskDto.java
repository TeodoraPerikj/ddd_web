package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;

import java.time.LocalDateTime;

@Getter
public class EachTaskDto implements ValueObject {

    @JsonProperty("id")
    private final TaskId id;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("startDate")
    private final LocalDateTime startDate;

    @JsonProperty("dueDate")
    private final LocalDateTime dueDate;

    @JsonProperty("estimationDays")
    private final Integer estimationDays;

    @JsonProperty("status")
    private final TaskStatus status;

    @JsonProperty("assignee")
    private final UserId assignee;

    @JsonProperty("creator")
    private final UserId creator;

    @JsonProperty("comment")
    private final CommentId comment;

    private EachTaskDto() {
        this.id = TaskId.randomId(TaskId.class);
        this.title = "";
        this.description = "";
        this.startDate = LocalDateTime.now();
        this.dueDate = LocalDateTime.now();
        this.estimationDays = 0;
        this.status = TaskStatus.TODO;
        this.assignee = UserId.randomId(UserId.class);
        this.creator = UserId.randomId(UserId.class);
        this.comment = CommentId.randomId(CommentId.class);
    }

    @JsonCreator
    public EachTaskDto(@JsonProperty("id") TaskId id,
                       @JsonProperty("title") String title,
                       @JsonProperty("description") String description,
                       @JsonProperty("startDate") LocalDateTime startDate,
                       @JsonProperty("dueDate") LocalDateTime dueDate,
                       @JsonProperty("estimationDays") Integer estimationDays,
                       @JsonProperty("status") TaskStatus status,
                       @JsonProperty("assignee") UserId assignee,
                       @JsonProperty("creator") UserId creator,
                       @JsonProperty("comment") CommentId comment) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.estimationDays = estimationDays;
        this.status = status;
        this.assignee = assignee;
        this.creator = creator;
        this.comment = comment;
    }

}
