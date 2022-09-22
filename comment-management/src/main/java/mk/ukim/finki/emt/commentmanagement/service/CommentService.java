package mk.ukim.finki.emt.commentmanagement.service;

import mk.ukim.finki.emt.commentmanagement.domain.model.Comment;
import mk.ukim.finki.emt.commentmanagement.domain.model.CommentId;
import mk.ukim.finki.emt.commentmanagement.service.form.CommentForm;

import java.util.List;

public interface CommentService {

    Comment create(CommentForm commentForm);

    Comment edit(CommentId id, String comment);

    void delete(CommentId id);

    Comment findById(CommentId id);

    //List<CommentsDto> showCommentsDto();

    List<Comment> listAll();

    //List<CommentsForTaskDto> findByCommentProjection(Long id);
}
