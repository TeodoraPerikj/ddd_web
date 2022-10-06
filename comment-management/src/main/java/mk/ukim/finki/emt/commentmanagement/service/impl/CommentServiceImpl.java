package mk.ukim.finki.emt.commentmanagement.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.emt.commentmanagement.domain.exceptions.CommentNotFoundException;
import mk.ukim.finki.emt.commentmanagement.domain.model.Comment;
import mk.ukim.finki.emt.commentmanagement.domain.model.CommentId;
import mk.ukim.finki.emt.commentmanagement.domain.repository.CommentRepository;
import mk.ukim.finki.emt.commentmanagement.domain.valueobjects.CommentsDto;
import mk.ukim.finki.emt.commentmanagement.service.CommentService;
import mk.ukim.finki.emt.commentmanagement.service.form.CommentForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Optional<Comment> create(CommentForm commentForm) {

        Objects.requireNonNull(commentForm, "comment form must not be null!");

        Comment comment = Comment.build(commentForm.getUserId(),
                commentForm.getTaskId(),
                commentForm.getComment(),
                LocalDateTime.now());

        commentRepository.saveAndFlush(comment);

        return Optional.of(comment);
    }

    @Override
    public Optional<Comment> edit(CommentId id, String comment) {

        Comment newComment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);

        newComment.editComment(comment);
        commentRepository.saveAndFlush(newComment);

        return Optional.of(newComment);
    }

    @Override
    public void delete(CommentId id) {

        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);

        commentRepository.delete(comment);
    }

    @Override
    public Optional<Comment> findById(CommentId id) {
        return Optional.of(commentRepository.findById(id).orElseThrow(CommentNotFoundException::new));
    }

    @Override
    public List<Comment> listAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<CommentsDto> showCommentsDto() {
        List<CommentsDto> commentsDtos = new ArrayList<>();

        for(Comment comment:this.commentRepository.findAll()){
           // commentsDtos.add(new CommentsDto(comment.getComment(),comment.getTask().getTitle()));
        }

        return commentsDtos;
    }
}
