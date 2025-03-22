package ru.kpfu.itis.kononenko.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.kononenko.model.Comment;
import ru.kpfu.itis.kononenko.repository.CommentRepository;
import ru.kpfu.itis.kononenko.util.WeatherUtil;

import java.util.List;


@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@RequestBody Comment comment) throws Exception {
        Double temperature = WeatherUtil.getTemperature(comment.getCity());
        comment.setTemperature(temperature);
        return commentRepository.save(comment);
    }

    @GetMapping
    @ResponseStatus (HttpStatus.OK)
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus (HttpStatus.OK)
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment commentData) {
        Comment comment = commentRepository.findById(id).orElseThrow();
        comment.setAuthorName(commentData.getAuthorName());
        comment.setCity(commentData.getCity());
        comment.setContent(commentData.getContent());
        comment.setTemperature(commentData.getTemperature());
        return commentRepository.save(comment);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long id) {
        commentRepository.deleteById(id);
    }
}
