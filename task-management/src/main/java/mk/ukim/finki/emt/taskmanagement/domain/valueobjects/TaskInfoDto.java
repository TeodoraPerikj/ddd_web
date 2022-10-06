package mk.ukim.finki.emt.taskmanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;
import mk.ukim.finki.emt.taskmanagement.domain.model.Task;

@Getter
public class TaskInfoDto implements ValueObject {

    @JsonProperty("task")
    private final Task task;

    @JsonProperty("textAndType")
    private final String textAndType;

//    @JsonProperty("assignee")
//    private final UserId assignee;

    @JsonProperty("assignee")
    private final String assignee;

    private TaskInfoDto() {
        this.task = new Task();
        this.textAndType = "";
        //this.assignee = UserId.randomId(UserId.class);
        this.assignee = "";
    }

    @JsonCreator
    public TaskInfoDto(@JsonProperty("task") Task task,
                       @JsonProperty("textAndType") String textAndType,
                       //@JsonProperty("assignee") UserId assignee)
                       @JsonProperty("assignee") String assignee){
        this.task = task;
        this.textAndType = textAndType;
        this.assignee = assignee;
    }
}
