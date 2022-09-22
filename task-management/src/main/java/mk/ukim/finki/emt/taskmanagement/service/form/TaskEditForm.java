package mk.ukim.finki.emt.taskmanagement.service.form;

import lombok.Data;
import mk.ukim.finki.emt.taskmanagement.domain.valueobjects.UserId;

import javax.validation.constraints.NotNull;

@Data
public class TaskEditForm {

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
    private UserId assignee;
}
