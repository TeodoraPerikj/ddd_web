package mk.ukim.finki.emt.usermanagement.domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.ValueObject;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MyUserTasksDto implements ValueObject {

    @JsonProperty("myToDoTasks")
    private final List<Task> myToDoTasks;

    @JsonProperty("myInProgressTasks")
    private final List<Task> myInProgressTasks;

    @JsonProperty("myDoneTasks")
    private final List<Task> myDoneTasks;

    @JsonProperty("myCanceledTasks")
    private final List<Task> myCanceledTasks;

    @JsonProperty("ownedTasks")
    private final List<Task> ownedTasks;

    @JsonProperty("numberOfTasks")
    private final List<Integer> numberOfTasks;

    @JsonProperty("activeUser")
    private final String activeUser;

    @JsonProperty("role")
    private final String role;

    private MyUserTasksDto(){
        this.myToDoTasks = new ArrayList<>();
        this.myInProgressTasks = new ArrayList<>();
        this.myDoneTasks = new ArrayList<>();
        this.myCanceledTasks = new ArrayList<>();
        this.ownedTasks = new ArrayList<>();
        this.numberOfTasks = new ArrayList<>();
        this.activeUser = "";
        this.role = "";
    }

    @JsonCreator
    public MyUserTasksDto(@JsonProperty("myToDoTasks") List<Task> myToDoTasks,
                          @JsonProperty("myInProgressTasks") List<Task> myInProgressTasks,
                          @JsonProperty("myDoneTasks") List<Task> myDoneTasks,
                          @JsonProperty("myCanceledTasks") List<Task> myCanceledTasks,
                          @JsonProperty("ownedTasks") List<Task> ownedTasks,
                          @JsonProperty("numberOfTasks") List<Integer> numberOfTasks,
                          @JsonProperty("activeUser") String activeUser,
                          @JsonProperty("role") String role){
        this.myToDoTasks = myToDoTasks;
        this.myInProgressTasks = myInProgressTasks;
        this.myDoneTasks = myDoneTasks;
        this.myCanceledTasks = myCanceledTasks;
        this.ownedTasks = ownedTasks;
        this.numberOfTasks = numberOfTasks;
        this.activeUser = activeUser;
        this.role = role;
    }
}

