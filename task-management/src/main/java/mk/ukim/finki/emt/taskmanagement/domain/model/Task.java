package mk.ukim.finki.emt.taskmanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Task extends AbstractEntity<TaskId> {

    @Column(unique = true, nullable = false)
    private String title;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;

    private Integer estimationDays;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private UserId creator;

    private UserId assignee;

    private CommentId comment;

    public Task(){
        super(TaskId.randomId(TaskId.class));
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeDescription(String description){
        this.description = description;
    }

    public void changeStartDate(LocalDateTime startDate){
        this.startDate = startDate;
    }

    public void changeDueDate(LocalDateTime dueDate){
        this.dueDate = dueDate;
    }

    public void changeEstimationDays(Integer estimationDays){
        this.estimationDays = estimationDays;
    }

    public void changeStatus(TaskStatus status){
        this.status = status;
    }

    public void changeAssignee(UserId assignee) {this.assignee = assignee;}

    public static Task build(String title, String description, LocalDateTime startDate, LocalDateTime dueDate,
                             Integer estimationDays, UserId creator, UserId assignee){

        Task task = new Task();

        task.title = title;
        task.description = description;
        task.startDate = startDate;
        task.dueDate = dueDate;
        task.estimationDays = estimationDays;
        task.status = TaskStatus.TODO;
        task.creator = creator;
        task.assignee = assignee;
        task.comment = CommentId.randomId(CommentId.class);

        return task;
    }
}
