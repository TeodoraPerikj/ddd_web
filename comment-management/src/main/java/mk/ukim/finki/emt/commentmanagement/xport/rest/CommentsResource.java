package mk.ukim.finki.emt.commentmanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.commentmanagement.domain.model.Comment;
import mk.ukim.finki.emt.commentmanagement.domain.model.CommentId;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.CommentsDto;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.TaskId;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.UserId;
import mk.ukim.finki.emt.commentmanagement.service.CommentService;
import mk.ukim.finki.emt.commentmanagement.service.form.CommentForm;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsResource {
    private final CommentService commentService;

    @GetMapping
    public List<CommentsDto> showComments(){
        return this.commentService.showCommentsDto();
    }

    @DeleteMapping("/{id}/deleteComment")
    public ResponseEntity deleteComment2(@PathVariable String id){

        CommentId commentId = new CommentId(id);
        this.commentService.delete(commentId);

        if(this.commentService.findById(commentId).isPresent())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/leaveComment")
    public ResponseEntity<Comment> addComment2(@PathVariable String id, @RequestParam String username,
                                               @RequestParam String comment){

        TaskId taskId = new TaskId(id);
        UserId userId = new UserId(username);

        CommentForm commentForm = new CommentForm();

        commentForm.setTaskId(taskId);
        commentForm.setUserId(userId);
        commentForm.setComment(comment);

        return this.commentService.create(commentForm)
                .map(comment1 -> ResponseEntity.ok().body(comment1))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
