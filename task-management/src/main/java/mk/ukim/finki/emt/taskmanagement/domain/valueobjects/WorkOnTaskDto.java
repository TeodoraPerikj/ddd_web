package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.taskmanagement.domain.model.Task;

import java.time.LocalDateTime;

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

    @JsonProperty("dateTime")
    private final LocalDateTime dateTime;

    @JsonProperty("commentUser")
    private final String commentUser;

    @JsonProperty("commentId")
    private final String commentId;

    @JsonProperty("creator")
    private final String creator;

    @JsonProperty("activeUser")
    private final String activeUser;

    @JsonProperty("role")
    private final String role;

    private WorkOnTaskDto() {
        this.task = new Task();
//        this.assignee = UserId.randomId(UserId.class);
//        this.comment = CommentId.randomId(CommentId.class);
        this.assignee = "";
        this.comment = "";
        this.dateTime = LocalDateTime.now();
        this.commentUser = "";
        this.commentId = "";
        this.creator = "";
        this.activeUser = "";
        this.role = "";
    }

    @JsonCreator
    public WorkOnTaskDto(@JsonProperty("task") Task task,
//                         @JsonProperty("assignee") UserId assignee,
//                         @JsonProperty("comment") CommentId commentId)
                         @JsonProperty("assignee") String assignee,
                         @JsonProperty("comment") String comment,
                         @JsonProperty("dateTime") LocalDateTime dateTime,
                         @JsonProperty("commentUser") String commentUser,
                         @JsonProperty("commentId") String commentId,
                         @JsonProperty("creator") String creator,
                         @JsonProperty("activeUser") String activeUser,
                         @JsonProperty("role") String role) {
        this.task = task;
        this.assignee = assignee;
        this.comment = comment;
        this.dateTime = dateTime;
        this.commentUser = commentUser;
        this.commentId = commentId;
        this.creator = creator;
        this.activeUser = activeUser;
        this.role = role;
    }
}
