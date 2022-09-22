package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TaskDto {
    @JsonProperty("title")
    private final String title;

    @JsonProperty("description")
    private final String description;

    @JsonProperty("startDate")
    private final String startDate;

    @JsonProperty("dueDate")
    private final String dueDate;

    @JsonProperty("estimationDays")
    private final String estimationDays;

    @JsonProperty("assignee")
    private final UserId assignee;

    @JsonProperty("creator")
    private final UserId creator;

    //// MAKE THEM STRING IF NEEDED

    private TaskDto() {
        this.title = "";
        this.description = "";
        this.startDate = "";
        this.dueDate = "";
        this.estimationDays = "";
        this.assignee = UserId.randomId(UserId.class);
        this.creator = UserId.randomId(UserId.class);
    }
    @JsonCreator
    public TaskDto(@JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("startDate") String startDate,
                   @JsonProperty("dueDate") String dueDate,
                   @JsonProperty("estimationDays") String estimationDays,
                   @JsonProperty("assignee") UserId assignee,
                   @JsonProperty("creator") UserId creator){
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.estimationDays = estimationDays;
        this.assignee = assignee;
        this.creator = creator;
    }
}
