package mk.ukim.finki.emt.usermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.usermanagement.domain.model.UserId;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class Task implements ValueObject {

    @JsonProperty("id")
    private final TaskId id;

    @JsonProperty("title")
    private final String title;

    @JsonProperty("description")
    private final String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("startDate")
    private final LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("dueDate")
    private final LocalDateTime dueDate;

    @JsonProperty("estimationDays")
    private final Integer estimationDays;

    @JsonProperty("status")
    private final TaskStatus status;

    @JsonProperty("creator")
    private final UserId creator;

    @JsonProperty("assignee")
    private final UserId assignee;

    @JsonProperty("comment")
    private final CommentId comment;

    private Task(){
        this.id = TaskId.randomId(TaskId.class);
        this.title = "";
        this.description = "";
        this.startDate = LocalDateTime.now();
        this.dueDate = LocalDateTime.now();
        this.estimationDays = 0;
        this.status = TaskStatus.TODO;
        this.creator = UserId.randomId(UserId.class);
        this.assignee = UserId.randomId(UserId.class);
        this.comment = CommentId.randomId(CommentId.class);
    }

    @JsonCreator
    public Task(@JsonProperty("id") TaskId id,
                @JsonProperty("title") String  title,
                @JsonProperty("description") String description,
                @JsonProperty("startDate") LocalDateTime startDate,
                @JsonProperty("dueDate") LocalDateTime dueDate,
                @JsonProperty("estimationDays") Integer estimationDays,
                @JsonProperty("creator") UserId creator,
                @JsonProperty("assignee") UserId assignee,
                @JsonProperty("comment") CommentId comment){
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.estimationDays = estimationDays;
        this.status = TaskStatus.TODO;
        this.creator = creator;
        this.assignee = assignee;
        this.comment = comment;
    }
}
