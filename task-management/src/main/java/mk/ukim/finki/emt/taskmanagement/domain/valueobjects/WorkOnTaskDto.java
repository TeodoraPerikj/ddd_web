package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.taskmanagement.domain.model.Task;

@Getter
public class WorkOnTaskDto implements ValueObject {

    @JsonProperty("task")
    private final Task task;

//    @JsonProperty("assignee")
//    private final UserId assignee;
//
//    @JsonProperty("comment")
//    private final CommentId comment;

    @JsonProperty("assignee")
    private final String assignee;

    @JsonProperty("comment")
    private final String comment;

    private WorkOnTaskDto() {
        this.task = new Task();
//        this.assignee = UserId.randomId(UserId.class);
//        this.comment = CommentId.randomId(CommentId.class);
        this.assignee = "";
        this.comment = "";
    }

    @JsonCreator
    public WorkOnTaskDto(@JsonProperty("task") Task task,
//                         @JsonProperty("assignee") UserId assignee,
//                         @JsonProperty("comment") CommentId commentId)
                         @JsonProperty("assignee") String assignee,
                         @JsonProperty("comment") String commentId) {
        this.task = task;
        this.assignee = assignee;
        this.comment = commentId;
    }
}
