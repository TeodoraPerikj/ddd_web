package mk.ukim.finki.emt.commentmanagement.service;

import mk.ukim.finki.emt.commentmanagement.domain.model.Comment;
import mk.ukim.finki.emt.commentmanagement.domain.model.CommentId;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.CommentsDto;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.TaskId;
import mk.ukim.finki.emt.commentmanagement.service.form.CommentForm;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<Comment> create(CommentForm commentForm);

    Optional<Comment> edit(CommentId id, String comment);

    boolean delete(CommentId commentId);

    Optional<Comment> findById(CommentId id);

    List<Comment> listAll();

    List<CommentsDto> showCommentsDto();

}
