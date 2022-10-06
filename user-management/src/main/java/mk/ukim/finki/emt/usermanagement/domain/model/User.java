package mk.ukim.finki.emt.usermanagement.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.Task;
import mk.ukim.finki.emt.usermanagement.domain.valueobjects.TaskId;

import javax.persistence.*;

@Entity
@Table(name = "app_user")
@Getter
public class User extends AbstractEntity<UserId> {

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("taskAssigned")
    private TaskId taskAssigned;

    @JsonProperty("taskOwned")
    private TaskId taskOwned;

    public User(){
        super(UserId.randomId(UserId.class));
    }

//    public User(String username){
//        super(new UserId(username));
//    }

    public static User build(String username, String name, String surname, String password){
        User user = new User();

        user.name = name;
        user.surname = surname;
        user.username = username;
        user.password = password;

        return user;
    }

    public static User createUserWithTasks(String username, String name, String surname, String password,
                                           TaskId taskAssigned, TaskId taskOwned){
        User user = new User();

        user.name = name;
        user.surname = surname;
        user.username = username;
        user.password = password;
        user.taskAssigned = taskAssigned;
        user.taskOwned = taskOwned;

        return user;
    }

    public void changeName(String name){
        this.name = name;
    }

    public void changeSurname(String surname){
        this.surname = surname;
    }

    public void changePassword(String password){
        this.password = password;
    }

    public void changeUsername(String username){this.username = username;}

    public void changeTaskAssigned(TaskId taskAssigned){
        this.taskAssigned = taskAssigned;
    }

    public void changeTaskOwned(TaskId taskOwned){
        this.taskOwned = taskOwned;
    }
}
