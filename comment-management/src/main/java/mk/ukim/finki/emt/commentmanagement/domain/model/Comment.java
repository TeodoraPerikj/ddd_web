package mk.ukim.finki.emt.commentmanagement.domain.model;

import lombok.Getter;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.TaskId;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.UserId;
import mk.ukim.finki.emt.sharedkernel.domain.base.AbstractEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
public class Comment extends AbstractEntity<CommentId> {

    @AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false))
    private UserId userId;

    @AttributeOverride(name = "id", column = @Column(name = "task_id", nullable = false))
    private TaskId taskId;

    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    private Comment(){
        super(CommentId.randomId(CommentId.class));
    }

    public static Comment build(UserId userId, TaskId taskId, String comment, LocalDateTime dateTime){
        Comment newComment = new Comment();

        newComment.userId = userId;
        newComment.taskId = taskId;
        newComment.comment = comment;
        newComment.dateTime = dateTime;

        return newComment;
    }

    public void editComment(String comment){
        this.comment = comment;
    }

}
