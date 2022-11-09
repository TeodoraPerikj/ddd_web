package mk.ukim.finki.emt.commentmanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.commentmanagement.domain.exceptions.CommentNotFoundException;
import mk.ukim.finki.emt.commentmanagement.domain.model.Comment;
import mk.ukim.finki.emt.commentmanagement.domain.model.CommentId;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.*;
import mk.ukim.finki.emt.commentmanagement.service.CommentService;
import mk.ukim.finki.emt.commentmanagement.service.form.CommentForm;
import mk.ukim.finki.emt.commentmanagement.xport.client.UserClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsResource {

    private final CommentService commentService;
    private final UserClient userClient;

    @GetMapping
    public List<CommentsDto> showComments(){
        return this.commentService.showCommentsDto();
    }

    @DeleteMapping("/{id}/deleteComment")
    public ResponseEntity deleteComment2(@PathVariable String id){

        CommentId commentId = new CommentId(id);

       try{
           this.commentService.delete(commentId);
           return ResponseEntity.ok().build();
       }
       catch (CommentNotFoundException exception){
           return ResponseEntity.badRequest().build();
       }
    }

    @PostMapping("/{id}/leaveComment")
    public ResponseEntity<Comment> addComment2(@PathVariable String id, @RequestParam String username,
                                               @RequestParam String comment){

        TaskId taskId = new TaskId(id);

        User user = this.userClient.findByUsername(username);

        UserId userId = user.getId();

        CommentForm commentForm = new CommentForm();

        commentForm.setTaskId(taskId);
        commentForm.setUserId(userId);
        commentForm.setComment(comment);

        return this.commentService.create(commentForm)
                .map(comment1 -> ResponseEntity.ok().body(comment1))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/findById")
    public ResponseEntity<Comment> findById(@RequestParam("commentId") CommentId commentId){

        return this.commentService.findById(commentId)
                .map(comment -> ResponseEntity.ok().body(comment))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete")
    public Boolean deleteComment(@RequestParam(required = false) CommentId commentId){

        if(commentId.getId().equals(""))
            return true;
        if(this.commentService.delete(commentId))
            return true;

        return false;
    }

    @DeleteMapping("/deleteComments")
    public Boolean deleteComments(@RequestParam UserId userId){


        if(this.commentService.deleteComments(userId))
            return true;

        return false;
    }
}
