package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

@Getter
public class TaskDto implements ValueObject {
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

//    @JsonProperty("assignee")
//    private final UserId assignee;
//
//    @JsonProperty("creator")
//    private final UserId creator;

    @JsonProperty("assignee")
    private final String assignee;

    @JsonProperty("creator")
    private final String creator;

    //// MAKE THEM STRING IF NEEDED

    private TaskDto() {
        this.title = "";
        this.description = "";
        this.startDate = "";
        this.dueDate = "";
        this.estimationDays = "";
//        this.assignee = UserId.randomId(UserId.class);
//        this.creator = UserId.randomId(UserId.class);
        this.assignee = "";
        this.creator = "";
    }
    @JsonCreator
    public TaskDto(@JsonProperty("title") String title,
                   @JsonProperty("description") String description,
                   @JsonProperty("startDate") String startDate,
                   @JsonProperty("dueDate") String dueDate,
                   @JsonProperty("estimationDays") String estimationDays,
//                   @JsonProperty("assignee") UserId assignee,
//                   @JsonProperty("creator") UserId creator){
                   @JsonProperty("assignee") String assignee,
                   @JsonProperty("creator") String creator){
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.estimationDays = estimationDays;
        this.assignee = assignee;
        this.creator = creator;
    }
}
