package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.taskmanagement.domain.model.Task;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class Comment implements ValueObject {
    @JsonProperty("id")
    private final CommentId id;

    @JsonProperty("user")
    private final User user;

    @JsonProperty("task")
    private final Task task;

    @JsonProperty("comment")
    private final String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("dateTime")
    private final LocalDateTime dateTime;

    private Comment() {
        this.id = CommentId.randomId(CommentId.class);
        this.task = new Task();
        this.user = new User();
        this.comment = "";
        this.dateTime = LocalDateTime.now();
    }

    @JsonCreator
    public Comment(@JsonProperty("id") CommentId id,
                   @JsonProperty("user") User user,
                   @JsonProperty("task") Task task,
                   @JsonProperty("comment") String commentId,
                   @JsonProperty("dateTime") LocalDateTime dateTime) {
        this.id = id;
        this.user = user;
        this.task = task;
        this.comment = commentId;
        this.dateTime = dateTime;
    }
}
