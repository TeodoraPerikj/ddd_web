package mk.ukim.finki.emt.taskmanagement.service.form;

import lombok.Data;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.CommentId;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.EstimationDays;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.UserId;

import javax.validation.constraints.NotNull;

@Data
public class TaskCreateForm {

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String startDate;

    @NotNull
    private String dueDate;

    @NotNull
    private Integer estimationDays;

    @NotNull
    private UserId creator;

    @NotNull
    private UserId assignee;
}
