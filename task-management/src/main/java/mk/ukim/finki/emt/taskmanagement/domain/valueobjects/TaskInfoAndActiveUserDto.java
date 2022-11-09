package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.taskmanagement.domain.model.Task;

@Getter
public class TaskInfoAndActiveUserDto implements ValueObject {

    @JsonProperty("task")
    private final Task task;

    @JsonProperty("textAndType")
    private final String textAndType;

//    @JsonProperty("assignee")
//    private final UserId assignee;

    @JsonProperty("assignee")
    private final String assignee;

    @JsonProperty("creator")
    private final String creator;

    @JsonProperty("activeUser")
    private final String activeUser;

    @JsonProperty("role")
    private final String role;

    private TaskInfoAndActiveUserDto() {
        this.task = new Task();
        this.textAndType = "";
        //this.assignee = UserId.randomId(UserId.class);
        this.assignee = "";
        this.creator = "";
        this.activeUser = "";
        this.role = "";
    }

    @JsonCreator
    public TaskInfoAndActiveUserDto(@JsonProperty("task") Task task,
                                    @JsonProperty("textAndType") String textAndType,
                                    //@JsonProperty("assignee") UserId assignee)
                                    @JsonProperty("assignee") String assignee,
                                    @JsonProperty("creator") String creator,
                                    @JsonProperty("activeUser") String activeUser,
                                    @JsonProperty("role") String role) {
        this.task = task;
        this.textAndType = textAndType;
        this.assignee = assignee;
        this.creator = creator;
        this.activeUser = activeUser;
        this.role = role;
    }
}
