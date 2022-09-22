package mk.ukim.finki.emt.commentmanagement.service.form;

import lombok.Data;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.TaskId;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.UserId;

import javax.validation.constraints.NotNull;

@Data
public class CommentForm {

    @NotNull
    private  UserId userId;

    @NotNull
    private TaskId taskId;

    @NotNull
    private String comment;
}
