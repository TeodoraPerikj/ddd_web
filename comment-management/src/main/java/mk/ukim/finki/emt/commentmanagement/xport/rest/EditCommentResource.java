package mk.ukim.finki.emt.commentmanagement.xport.rest;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.commentmanagement.domain.model.Comment;
import mk.ukim.finki.emt.commentmanagement.domain.model.CommentId;
import mk.ukim.finki.emt.commentmanagement.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/editComment")
@AllArgsConstructor
public class EditCommentResource {

    private final CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<Comment> editCommentPage2(@PathVariable String id){

        CommentId commentId = new CommentId(id);

        return this.commentService.findById(commentId)
                .map(comment -> ResponseEntity.ok().body(comment))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> editComment2(@PathVariable String id, @RequestParam String editComment){

        CommentId commentId = new CommentId(id);

        return this.commentService.edit(commentId, editComment)
                .map(comment -> ResponseEntity.ok().body(comment))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
