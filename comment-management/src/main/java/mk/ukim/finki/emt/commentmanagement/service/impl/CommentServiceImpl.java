package mk.ukim.finki.emt.commentmanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.commentmanagement.domain.exceptions.CommentNotFoundException;
import mk.ukim.finki.emt.commentmanagement.domain.model.Comment;
import mk.ukim.finki.emt.commentmanagement.domain.model.CommentId;
import mk.ukim.finki.emt.commentmanagement.domain.repository.CommentRepository;
import mk.ukim.finki.emt.commentmanagement.service.CommentService;
import mk.ukim.finki.emt.commentmanagement.service.form.CommentForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Comment create(CommentForm commentForm) {

        Objects.requireNonNull(commentForm, "comment form must not be null!");

        Comment comment = Comment.build(commentForm.getUserId(),
                commentForm.getTaskId(),
                commentForm.getComment(),
                LocalDateTime.now());

        commentRepository.saveAndFlush(comment);

        return comment;
    }

    @Override
    public Comment edit(CommentId id, String comment) {

        Comment newComment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);

        newComment.editComment(comment);
        commentRepository.saveAndFlush(newComment);

        return newComment;
    }

    @Override
    public void delete(CommentId id) {

        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);

        commentRepository.delete(comment);
    }

    @Override
    public Comment findById(CommentId id) {
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    @Override
    public List<Comment> listAll() {
        return commentRepository.findAll();
    }
}
