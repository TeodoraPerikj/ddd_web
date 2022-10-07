package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.taskmanagement.domain.model.Task;
import mk.ukim.finki.emt.taskmanagement.domain.model.TaskId;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class Comment implements ValueObject {
    @JsonProperty("id")
    private final CommentId id;

    @JsonProperty("userId")
    private final UserId userId;

    @JsonProperty("taskId")
    private final TaskId taskId;

    @JsonProperty("comment")
    private final String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("dateTime")
    private final LocalDateTime dateTime;

    private Comment() {
        this.id = CommentId.randomId(CommentId.class);
        this.taskId = TaskId.randomId(TaskId.class);
        this.userId = UserId.randomId(UserId.class);
        this.comment = "";
        this.dateTime = LocalDateTime.now();
    }

    @JsonCreator
    public Comment(@JsonProperty("id") CommentId id,
                   @JsonProperty("userId") UserId userId,
                   @JsonProperty("taskId") TaskId taskId,
                   @JsonProperty("comment") String commentId,
                   @JsonProperty("dateTime") LocalDateTime dateTime) {
        this.id = id;
        this.userId = userId;
        this.taskId = taskId;
        this.comment = commentId;
        this.dateTime = dateTime;
    }
}
